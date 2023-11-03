package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepositorySearch {

    @Autowired
    EntityManager em;

    @Override
    public List<Comment> allCommentsBySearchCriteria(CommentSearchCriteria commentSearchCriteria) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);

        final Root<Comment> commentTable = cq.from(Comment.class);

        final List<Predicate> predicates = new ArrayList<>();
        // Post
        if (commentSearchCriteria.post() != null) {
            predicates.add(cb.equal(commentTable.get("post"), commentSearchCriteria.post()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        // @todo determine sort/pagination
        cq.orderBy(cb.desc(commentTable.get("updatedAt")));

        return em.createQuery(cq).getResultList();
    }
}
