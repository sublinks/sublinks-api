package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.PostHistory;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class PostHistoryRepositoryImpl implements PostHistoryRepositoryExtended {

  private final EntityManager em;

  @Transactional
  public int deleteAllByCreator(@NonNull Person creator) {

    final NativeQuery<PostHistory> query = (NativeQuery<PostHistory>) em.createNativeQuery(
        "DELETE FROM post_history WHERE post_id IN (SELECT p.id FROM posts p JOIN link_person_posts lpp ON lpp.post_id = p.id  WHERE lpp.person_id = :creatorId AND lpp.link_type = 'creator')");
    query.setParameter("creatorId", creator.getId());
    em.joinTransaction();
    return query.executeUpdate();
  }
}
