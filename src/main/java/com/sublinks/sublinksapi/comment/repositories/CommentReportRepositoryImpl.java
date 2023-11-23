package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentRead;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
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

public class CommentReportRepositoryImpl implements CommentReportRepositorySearch {

  @Autowired
  EntityManager em;

  @Override
  public List<CommentReport> allCommentReportsBySearchCriteria(
      CommentReportSearchCriteria commentReportSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<CommentReport> cq = cb.createQuery(CommentReport.class);

    final Root<CommentReport> commentTable = cq.from(CommentReport.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (commentReportSearchCriteria.unresolvedOnly()) {
      predicates.add(cb.equal(commentTable.get("resolved"), false));
    }

    if (commentReportSearchCriteria.community() != null) {
      // Join Comment and check community id
      final Join<CommentReport, Comment> commentJoin = commentTable.join("comment", JoinType.LEFT);
      commentReportSearchCriteria.community().forEach(community -> {
        predicates.add(cb.equal(commentJoin.get("community"), community));
      });
    }

    cq.where(predicates.toArray(new Predicate[0]));

    cq.orderBy(cb.desc(commentTable.get("createdAt")));

    int perPage = Math.min(Math.abs(commentReportSearchCriteria.perPage()), 20);
    int page = Math.max(commentReportSearchCriteria.page() - 1, 0);

    TypedQuery<CommentReport> query = em.createQuery(cq);
    query.setMaxResults(perPage);
    query.setFirstResult(page * perPage);

    return query.getResultList();
  }
}
