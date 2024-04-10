package com.sublinks.sublinksapi.comment.repositories;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.community.entities.Community;
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
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentReportRepositoryImpl implements CommentReportRepositorySearch {

  private final EntityManager em;

  @Override
  public List<CommentReport> allCommentReportsBySearchCriteria(
      CommentReportSearchCriteria commentReportSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<CommentReport> cq = cb.createQuery(CommentReport.class);

    final Root<CommentReport> commentReportTable = cq.from(CommentReport.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (commentReportSearchCriteria.unresolvedOnly()) {
      predicates.add(cb.equal(commentReportTable.get("resolved"), false));
    }

    if (commentReportSearchCriteria.community() != null) {
      // Join Comment and check community id
      final Join<CommentReport, Comment> commentJoin = commentReportTable.join("comment",
          JoinType.LEFT);

      List<Predicate> communityPredicates = new ArrayList<>();

      commentReportSearchCriteria.community().forEach(community -> {
        // Add a Or condition for each community

        communityPredicates.add(cb.equal(commentJoin.get("community"), community));
      });

      predicates.add(cb.or(communityPredicates.toArray(new Predicate[0])));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    cq.orderBy(cb.desc(commentReportTable.get("createdAt")));

    int perPage = Math.min(Math.abs(commentReportSearchCriteria.perPage()), 20);

    TypedQuery<CommentReport> query = em.createQuery(cq);

    applyPagination(query, commentReportSearchCriteria.page(), perPage);

    return query.getResultList();
  }

  @Override
  public long countAllCommentReportsByResolvedFalseAndCommunity(
      @Nullable List<Community> communities) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Long> cq = cb.createQuery(Long.class);

    final Root<CommentReport> commentReportTable = cq.from(CommentReport.class);

    final List<Predicate> predicates = new ArrayList<>();

    predicates.add(cb.equal(commentReportTable.get("resolved"), false));

    if (communities != null) {
      // Join Comment and check community id
      final Join<CommentReport, Comment> commentJoin = commentReportTable.join("comment",
          JoinType.LEFT);

      List<Predicate> communityPredicates = new ArrayList<>();

      communities.forEach(community -> {
        // Add a Or condition for each community

        communityPredicates.add(cb.equal(commentJoin.get("community"), community));
      });

      predicates.add(cb.or(communityPredicates.toArray(new Predicate[0])));

    }

    cq.where(predicates.toArray(new Predicate[0]));
    cq.select(cb.count(commentReportTable));
    return em.createQuery(cq).getSingleResult();
  }

  @Override
  public long countAllCommentReportsByResolvedFalse() {

    return countAllCommentReportsByResolvedFalseAndCommunity(null);
  }
}
