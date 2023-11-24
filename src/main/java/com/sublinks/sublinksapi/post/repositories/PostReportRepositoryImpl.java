package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.models.PostReportSearchCriteria;
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
      // Join Comment and check community id
      final Join<PostReport, Post> postJoin = postTable.join("post", JoinType.LEFT);
      postReportSearchCriteria.community().forEach(community -> {
        predicates.add(cb.equal(postJoin.get("community"), community));
      });
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
}
