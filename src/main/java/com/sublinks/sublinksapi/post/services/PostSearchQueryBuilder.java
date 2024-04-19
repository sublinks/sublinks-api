package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostLike;
import com.sublinks.sublinksapi.post.entities.Post_;
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
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PostSearchQueryBuilder {

  private final EntityManager entityManager;

  public Builder builder() {

    return new Builder(entityManager, entityManager.getCriteriaBuilder());
  }

  public Results results(Builder builder) {

    return new Results(builder);
  }

  @Getter
  public static class Results {

    private final Builder builder;
    private int perPage = 20;

    public Results(
        Builder builder
    ) {

      this.builder = builder;
    }

    public Results setSortType(final SortType sortType) {

      if (Objects.requireNonNull(sortType) == SortType.Old) {
        builder.getCriteriaQuery()
            .orderBy(builder.getCriteriaBuilder()
                .asc(builder.getPostTable().get("createdAt")));
      } else {
        builder.getCriteriaQuery()
            .orderBy(builder.getCriteriaBuilder()
                .desc(builder.getPostTable().get("createdAt")));
      }
      return this;
    }

    public Results setPerPage(final Integer perPage) {

      this.perPage = Math.min(Math.abs(perPage), 20);
      return this;
    }

    public Results setPage(final Integer page) {

      int p = Math.max(Math.abs(page), 0);
      getQuery().setFirstResult((p - 1) * this.getPerPage());
      return this;
    }


    private TypedQuery<Post> getQuery() {

      builder.getCriteriaQuery()
          .where(builder.getPredicates().toArray(new Predicate[0]));

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
    private final Root<Post> postTable;
    final List<Predicate> predicates = new ArrayList<>();
    private final CriteriaQuery<Post> criteriaQuery;
    private Person person;

    public Builder(
        EntityManager entityManager,
        CriteriaBuilder criteriaBuilder
    ) {

      this.entityManager = entityManager;
      this.criteriaBuilder = criteriaBuilder;
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

    public Builder addPersonLikesToPost() {

      if (this.person == null) {
        return this;
      }

      final Join<Post, PostLike> postPostLikeJoin = postTable.join("postLikes", JoinType.LEFT);
      postPostLikeJoin.on(criteriaBuilder.equal(postPostLikeJoin.get("person"), this.person));

      return this;
    }

    public Builder setCursor(final String cursor) {

      byte[] decodedBytes = Base64.getDecoder().decode(cursor);
      String decodedString = new String(decodedBytes);

      predicates.add(criteriaBuilder.greaterThan(postTable.get(Post_.ID), decodedString));
      return this;
    }

    public Builder setPerson(Person person) {

      this.person = person;
      return this;
    }
  }
}
