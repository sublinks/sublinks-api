package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentHistory;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.PostHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommentHistoryRepositoryImpl implements CommentHistoryRepositoryExtended {

  private final EntityManager em;

  public int deleteAllByCreator(@NonNull Person creator) {


    final NativeQuery<PostHistory> query = (NativeQuery<PostHistory>) em.createNativeQuery(
        "DELETE FROM comment_history WHERE comment_id IN (SELECT c.id FROM comments c WHERE c.person_id = :creatorId)");
    query.setParameter("creatorId", creator.getId());
    em.joinTransaction();
    return query.executeUpdate();
  }
}
