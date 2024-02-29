package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentHistory;
import com.sublinks.sublinksapi.person.dto.LinkPersonPost;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
