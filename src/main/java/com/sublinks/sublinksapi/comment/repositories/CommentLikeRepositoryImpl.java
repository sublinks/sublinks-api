package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.models.CommentLikeSearchCriteria;
import com.sublinks.sublinksapi.comment.models.CommentReplySearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

@AllArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepositorySearch {

  private final EntityManager em;

  public List<CommentLike> allCommentLikesBySearchCriteria(
      CommentLikeSearchCriteria commentLikeSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<CommentLike> cq = cb.createQuery(CommentLike.class);

    final Root<CommentLike> commentLikeTable = cq.from(CommentLike.class);

    final List<Predicate> predicates = new ArrayList<>();

    predicates.add(
        cb.equal(commentLikeTable.get("comment").get("id"), commentLikeSearchCriteria.commentId()));

    cq.where(predicates.toArray(new Predicate[0]));

    int perPage = Math.min(Math.abs(commentLikeSearchCriteria.perPage()), 20);

    TypedQuery<CommentLike> query = em.createQuery(cq);

    applyPagination(query, commentLikeSearchCriteria.page(), perPage);

    return query.getResultList();
  }
}
