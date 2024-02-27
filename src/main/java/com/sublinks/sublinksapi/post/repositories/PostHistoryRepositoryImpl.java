package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentHistory;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PostHistoryRepositoryImpl implements PostHistoryRepositoryExtended {

  private final EntityManager em;

  public int deleteAllByCreator(@NonNull Person creator) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaUpdate<PostHistory> cq = cb.createCriteriaUpdate(PostHistory.class);

    final Root<PostHistory> postHistoryTable = cq.from(PostHistory.class);

    final List<Predicate> predicates = new ArrayList<>();

    final Join<PostHistory, Post> postJoin = postHistoryTable.join("post");

    predicates.add(cb.and(cb.equal(postJoin.get("linkPersonPost").get("person"), creator),
        cb.equal(postJoin.get("linkPersonPost").get("linkType"), LinkPersonPostType.creator)));

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).executeUpdate();
  }
}
