package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessage;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivateMessageRepositoryImpl implements PrivateMessageRepositorySearch {

  @Autowired
  EntityManager em;


  @Override
  public List<PrivateMessage> allPrivateMessagesBySearchCriteria(
      PrivateMessageSearchCriteria privateMessageSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<PrivateMessage> cq = cb.createQuery(PrivateMessage.class);
    final Root<PrivateMessage> privateMessageTable = cq.from(PrivateMessage.class);
    final List<Predicate> predicates = new ArrayList<>();

    if (privateMessageSearchCriteria.unresolvedOnly()) {
      predicates.add(cb.equal(privateMessageTable.get("resolved"), false));
    }

    if (privateMessageSearchCriteria.person() != null) {
      predicates.add(cb.or(
          cb.equal(privateMessageTable.get("recipient"), privateMessageSearchCriteria.person()),
          cb.equal(privateMessageTable.get("sender"), privateMessageSearchCriteria.person())));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    switch (privateMessageSearchCriteria.privateMessageSortType()) {
      case New:
        cq.orderBy(cb.desc(privateMessageTable.get("createdAt")));
        break;
      case Old:
        cq.orderBy(cb.asc(privateMessageTable.get("createdAt")));
        break;
      default:
        cq.orderBy(cb.desc(privateMessageTable.get("createdAt")));
        break;
    }

    int perPage = Math.min(Math.abs(privateMessageSearchCriteria.perPage()), 20);
    int page = Math.max(privateMessageSearchCriteria.page() - 1, 0);

    TypedQuery<PrivateMessage> query = em.createQuery(cq);
    query.setMaxResults(perPage);
    query.setFirstResult(page * perPage);

    return query.getResultList();
  }
}
