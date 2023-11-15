package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import com.sublinks.sublinksapi.person.models.PersonMentionSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PersonMentionRepositorySearchImpl implements PersonMentionRepositorySearch {

    @Autowired
    EntityManager em;

    @Override
    public List<PersonMention> allPersonMentionBySearchCriteria(PersonMentionSearchCriteria personMentionSearchCriteria) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersonMention> cq = cb.createQuery(PersonMention.class);

        final Root<PersonMention> postMentionTable = cq.from(PersonMention.class);

        final List<Predicate> predicates = new ArrayList<>();

        // Join for CommentView
        if (!personMentionSearchCriteria.unreadOnly()) {
            predicates.add(cb.equal(postMentionTable.get("isRead"), false));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        // @todo determine sort/pagination
        cq.orderBy(cb.desc(postMentionTable.get("createdAt")));

        return em.createQuery(cq).getResultList();
    }
}
