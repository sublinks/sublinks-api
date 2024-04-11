package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.PostHistory;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.lang.NonNull;

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
