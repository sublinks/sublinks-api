package com.sublinks.sublinksapi.post.repositories;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PostRepositoryImpl implements PostRepositorySearch {

  @Autowired
  EntityManager em;

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

    applyPagination(query, postSearchCriteria.page(), perPage);

    return query.getResultList();
  }
}
