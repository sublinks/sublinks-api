package com.sublinks.sublinksapi.post.repositories;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.dto.LinkPersonPost;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.shared.RemovedState;
import com.sublinks.sublinksapi.utils.CursorBasedPageable;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;

@AllArgsConstructor
public class PostRepositoryImpl implements PostRepositorySearch {

  private final EntityManager em;

  @Override
  public List<Post> allPostsBySearchCriteria(final PostSearchCriteria postSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Post> cq = cb.createQuery(Post.class);

    final Root<Post> postTable = cq.from(Post.class);
    final Join<Post, Community> communityJoin = postTable.join("community", JoinType.INNER);

    final List<Predicate> predicates = new ArrayList<>();
    // Community filter
    if (postSearchCriteria.communityIds() != null && !postSearchCriteria.communityIds().isEmpty()) {
      final Expression<Long> expression = communityJoin.get("id");
      predicates.add(expression.in(postSearchCriteria.communityIds()));
    }
    // Subscribed only filter
    if (postSearchCriteria.person() != null
        && postSearchCriteria.listingType() == ListingType.Subscribed) {
      final Join<Community, LinkPersonCommunity> linkPersonCommunityJoin = communityJoin.join(
          "linkPersonCommunity", JoinType.INNER);
      predicates.add(cb.equal(linkPersonCommunityJoin.get("person"), postSearchCriteria.person()));
    }
    // Join for PostLike
    if (postSearchCriteria.person() != null) {
      final Join<Post, PostLike> postPostLikeJoin = postTable.join("postLikes", JoinType.LEFT);
      postPostLikeJoin.on(cb.equal(postPostLikeJoin.get("person"), postSearchCriteria.person()));
    }

    CursorBasedPageable<Post> cursorBasedPageable = postSearchCriteria.cursorBasedPageable();

    if (cursorBasedPageable != null) {
      try {
        final Map<String, String> values = cursorBasedPageable.getNextPageDecodedCursor();
        final String primary = values.get("primary");
        // 2024-03-17T11:07:39.000057+01 format

        final Date secondary = new SimpleDateFormat(DateUtils.FRONT_END_DATE_FORMAT).parse(
            values.get("secondary"));

        final Predicate primaryPredicate = cb.lessThan(postTable.get("id"),
            Integer.parseInt(primary));
        final Predicate secondaryPredicate = switch (postSearchCriteria.sortType()) {
          case New -> cb.lessThan(postTable.<Date>get("createdAt"), secondary);
          case Old -> cb.greaterThan(postTable.<Date>get("createdAt"), secondary);
          default -> cb.lessThan(postTable.<Date>get("createdAt"), secondary);
        };
        final Predicate secondaryPrimaryPredicate = cb.equal(postTable.get("createdAt"), primary);
        predicates.add(
            cb.or(cb.and(primaryPredicate, secondaryPrimaryPredicate), secondaryPredicate));
      } catch (Exception e) {
        System.out.println("Error in parsing date");
        throw new IllegalArgumentException("Error in parsing Cursor");
      }
    }

    cq.where(predicates.toArray(new Predicate[0]));

    switch (postSearchCriteria.sortType()) {
      case New:
        cq.orderBy(cb.desc(postTable.get("createdAt")));
        break;
      case Old:
        cq.orderBy(cb.asc(postTable.get("createdAt")));
        break;
      default:
        cq.orderBy(cb.desc(postTable.get("createdAt")));
        break;
    }
    int perPage = Math.min(Math.abs(postSearchCriteria.perPage()), 20);

    TypedQuery<Post> query = em.createQuery(cq);
    if (cursorBasedPageable == null) {
      applyPagination(query, postSearchCriteria.page(), perPage);
    } else {
      applyPagination(query, null, perPage);
    }

    return query.getResultList();
  }

  @Override
  public List<Post> allPostsByCommunityAndPersonAndRemoved(Community community, Person person,
      List<RemovedState> removedStates) {

    if (community == null || person == null) {
      throw new IllegalArgumentException("Community and person cannot be null");
    }

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Post> cq = cb.createQuery(Post.class);

    final Root<Post> postTable = cq.from(Post.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (removedStates != null) {
      final Expression<RemovedState> expression = postTable.get("removedState");
      predicates.add(expression.in(removedStates));
    }

    predicates.add(cb.equal(postTable.get("community"), community));

    final Join<Post, LinkPersonPost> linkPersonPostJoin = postTable.join("linkPersonPost",
        JoinType.INNER);
    predicates.add(cb.equal(linkPersonPostJoin.get("person"), person));
    predicates.add(cb.equal(linkPersonPostJoin.get("linkType"), LinkPersonPostType.creator));

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }

  @Override
  public List<Post> allPostsByPersonAndRemoved(@Nullable Person person,
      @Nullable List<RemovedState> removedStates) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Post> cq = cb.createQuery(Post.class);

    final Root<Post> postTable = cq.from(Post.class);

    final List<Predicate> predicates = new ArrayList<>();

    // Not removed filter
    if (removedStates != null) {
      final Expression<RemovedState> expression = postTable.get("removedState");
      predicates.add(expression.in(removedStates));
    }

    if (person != null) {
      final Join<Post, LinkPersonPost> linkPersonPostJoin = postTable.join("linkPersonPost",
          JoinType.INNER);
      predicates.add(cb.equal(linkPersonPostJoin.get("person"), person));
      predicates.add(cb.equal(linkPersonPostJoin.get("linkType"), LinkPersonPostType.creator));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }

}
