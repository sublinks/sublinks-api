package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.models.PostReportSearchCriteria;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PostReportRepositoryImpl implements PostReportRepositorySearch {

  @Autowired
  EntityManager em;

  @Override
  public List<PostReport> allPostReportsBySearchCriteria(
      PostReportSearchCriteria postReportSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<PostReport> cq = cb.createQuery(PostReport.class);

    final Root<PostReport> postTable = cq.from(PostReport.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (postReportSearchCriteria.unresolvedOnly()) {
      predicates.add(cb.equal(postTable.get("resolved"), false));
    }

    if (postReportSearchCriteria.community() != null) {
      // Join PostG and check community id
      final Join<PostReport, Post> postJoin = postTable.join("post", JoinType.LEFT);
      List<Predicate> communityPredicates = new ArrayList<>();
      postReportSearchCriteria.community().forEach(community -> {

        communityPredicates.add(cb.equal(postJoin.get("community"), community));
      });
      predicates.add(cb.or(communityPredicates.toArray(new Predicate[0])));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    cq.orderBy(cb.desc(postTable.get("createdAt")));

    int perPage = Math.min(Math.abs(postReportSearchCriteria.perPage()), 20);
    int page = Math.max(postReportSearchCriteria.page() - 1, 0);

    TypedQuery<PostReport> query = em.createQuery(cq);
    query.setMaxResults(perPage);
    query.setFirstResult(page * perPage);

    return query.getResultList();
  }

  @Override
  public long countAllPostReportsByResolvedFalseAndCommunity(
      @Nullable List<Community> communities) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Long> cq = cb.createQuery(Long.class);

    final Root<PostReport> postReportTable = cq.from(PostReport.class);

    final List<Predicate> predicates = new ArrayList<>();

    predicates.add(cb.equal(postReportTable.get("resolved"), false));

    if (communities != null) {
      // Join Comment or check community id
      final Join<PostReport, Post> postJoin = postReportTable.join("post", JoinType.LEFT);
      List<Predicate> communityPredicates = new ArrayList<>();
      communities.forEach(community -> {
        // Add a Or condition for each community

        communityPredicates.add(cb.equal(postJoin.get("community"), community));
      });
      predicates.add(cb.or(communityPredicates.toArray(new Predicate[0])));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    cq.select(cb.count(postReportTable));

    return em.createQuery(cq).getSingleResult();
  }

  @Override
  public long countAllPostReportsReportsByResolvedFalse() {

    return countAllPostReportsByResolvedFalseAndCommunity(null);
  }
}
