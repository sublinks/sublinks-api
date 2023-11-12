package com.sublinks.sublinksapi.private_messages.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentRead;
import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import com.sublinks.sublinksapi.private_messages.models.PrivateMessageSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PrivateMessageRepositoryImpl implements PrivateMessageRepositorySearch {

    @Autowired
    EntityManager em;


    @Override
    public List<PrivateMessage> allPrivateMessagesBySearchCriteria(PrivateMessageSearchCriteria privateMessageSearchCriteria) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PrivateMessage> cq = cb.createQuery(PrivateMessage.class);

        final Root<PrivateMessage> privateMessageTable = cq.from(PrivateMessage.class);

        final List<Predicate> predicates = new ArrayList<>();

        if(privateMessageSearchCriteria.unresolvedOnly()) {
            predicates.add(cb.equal(privateMessageTable.get("resolved"), false));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        // @todo determine sort/pagination
        cq.orderBy(cb.desc(privateMessageTable.get("updatedAt")));
        return em.createQuery(cq).getResultList();
    }
}
