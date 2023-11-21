package com.sublinks.sublinksapi.private_messages.repositories;

import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import com.sublinks.sublinksapi.private_messages.models.PrivateMessageSearchCriteria;
import jakarta.persistence.EntityManager;
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

    // @todo determine sort/pagination
    cq.orderBy(cb.desc(privateMessageTable.get("updatedAt")));
    return em.createQuery(cq).getResultList();
  }
}
