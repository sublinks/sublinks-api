package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostLike;
import com.sublinks.sublinksapi.post.sorts.SortFactory;
import com.sublinks.sublinksapi.post.sorts.SortingTypeInterface;
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
import java.util.Base64;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PostSearchQueryService {

  private final EntityManager entityManager;
  private final SortFactory sortFactory;

  public Builder builder() {

    return new Builder(entityManager, entityManager.getCriteriaBuilder(), sortFactory);
  }

  public Results results(Builder builder) {

    return new Results(builder);
  }

  @Getter
  public static class Results {

    private final Builder builder;
    private int perPage = 20;

    public Results(Builder builder)
    {

      this.builder = builder;
    }

    public Results setPerPage(final Integer perPage) {

      this.perPage = Math.min(Math.abs(perPage), 21);
      return this;
    }

    public Results setPage(final Integer page) {

      int p = Math.max(Math.abs(page), 0);
      getQuery().setFirstResult((p - 1) * this.getPerPage());
      return this;
    }

    private TypedQuery<Post> getQuery() {

      if (builder.getSorter() != null) {
        builder.getSorter()
            .applySorting(builder);
        if (builder.getCursor() != null && !builder.getCursor()
            .isBlank()) {
          builder.getSorter()
              .applyCursor(builder);
        }
      }

      builder.getCriteriaQuery()
          .where(builder.getPredicates()
              .toArray(new Predicate[0]));

      return builder.getEntityManager()
          .createQuery(builder.getCriteriaQuery());
    }

    public List<Post> getResults() {

      final TypedQuery<Post> query = getQuery();

      query.setMaxResults(Math.abs(this.perPage));

      return query.getResultList();
    }

    public List<Post> getCursorResults() {

      final TypedQuery<Post> query = getQuery();

      query.setMaxResults(Math.abs(this.perPage));

      return query.getResultList();
    }
  }

  @Getter
  public static class Builder {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final SortFactory sortFactory;
    private final Root<Post> postTable;
    final List<Predicate> predicates = new ArrayList<>();
    private final CriteriaQuery<Post> criteriaQuery;
    private Person person;
    private String cursor;
    private SortingTypeInterface sorter;

    public Builder(EntityManager entityManager, CriteriaBuilder criteriaBuilder,
        SortFactory sortFactory)
    {

      this.entityManager = entityManager;
      this.criteriaBuilder = criteriaBuilder;
      this.sortFactory = sortFactory;
      this.criteriaQuery = criteriaBuilder.createQuery(Post.class);
      this.postTable = criteriaQuery.from(Post.class);
    }

    public Builder filterByCommunities(List<Long> communityIds) {

      final Join<Post, Community> communityJoin = postTable.join("community", JoinType.INNER);
      if (communityIds != null && communityIds.isEmpty()) {
        final Expression<Long> expression = communityJoin.get("id");
        predicates.add(expression.in(communityIds));
      }
      return this;
    }

    public Builder filterByListingType(ListingType listingType, Person person) {

      this.setPerson(person);
      return this.filterByListingType(listingType);
    }

    public Builder filterByListingType(ListingType listingType) {

      if (this.person == null) {
        return this;
      }

      final Join<Post, Community> communityJoin = postTable.join("community", JoinType.INNER);
      final Join<Community, LinkPersonCommunity> linkPersonCommunityJoin = communityJoin.join(
          "linkPersonCommunity", JoinType.INNER);
      predicates.add(criteriaBuilder.equal(linkPersonCommunityJoin.get("person"), this.person));

      return this;
    }

    public Builder addPersonLikesToPost(Person person) {

      this.setPerson(person);
      return addPersonLikesToPost();
    }

    public Builder addPersonLikesToPost() {

      if (this.person == null) {
        return this;
      }

      final Join<Post, PostLike> postPostLikeJoin = postTable.join("postLikes", JoinType.LEFT);
      postPostLikeJoin.on(criteriaBuilder.equal(postPostLikeJoin.get("person"), this.person));

      return this;
    }

    public Builder setSorter(final SortingTypeInterface sorter) {

      this.sorter = sorter;
      return this;
    }

    public Builder setSortType(final SortType sortType) {

      return setSorter(sortFactory.createSort(sortType));
    }

    public Builder setCursor(final String cursor) {

      byte[] decodedBytes = Base64.getDecoder()
          .decode(cursor);
      this.cursor = new String(decodedBytes);
      return this;
    }

    public Builder setPerson(Person person) {

      this.person = person;
      return this;
    }

    public Builder setShowNsfw(Boolean isShowNsfw) {

      if (!isShowNsfw) {
        predicates.add(criteriaBuilder.isFalse(postTable.get("isNsfw")));
      }
      return this;
    }

    public Builder addSearch(String search) {

      if (search != null && !search.isBlank()) {

        predicates.add(criteriaBuilder.equal(
            criteriaBuilder.function("fn_search_vector_is_same", Boolean.class,
                postTable.get("searchVector"), criteriaBuilder.literal(search)), true));
      }
      return this;
    }
  }
}
