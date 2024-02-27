package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentHistory;
import com.sublinks.sublinksapi.person.dto.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommentHistoryRepositoryImpl implements CommentHistoryRepositoryExtended {

  private final EntityManager em;

  public int deleteAllByCreator(@NonNull Person Creator) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaUpdate<CommentHistory> cq = cb.createCriteriaUpdate(CommentHistory.class);

    final Root<CommentHistory> commentTHistoryTable = cq.from(CommentHistory.class);

    final List<Predicate> predicates = new ArrayList<>();

    predicates.add(cb.equal(commentTHistoryTable.get("comment").get("person"), Creator));

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).executeUpdate();
  }
}
