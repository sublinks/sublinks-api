package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessage;
import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessageReport;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageReportSearchCriteria;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivateMessageReportRepositoryImpl implements PrivateMessageReportSearchRepository {

  @Autowired
  EntityManager em;


  @Override
  public List<PrivateMessageReport> allPrivateMessageReportsBySearchCriteria(
      PrivateMessageReportSearchCriteria privateMessageReportSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<PrivateMessageReport> cq = cb.createQuery(PrivateMessageReport.class);
    final Root<PrivateMessageReport> privateMessageTable = cq.from(PrivateMessageReport.class);
    final List<Predicate> predicates = new ArrayList<>();

    if (privateMessageReportSearchCriteria.unresolvedOnly()) {
      predicates.add(cb.equal(privateMessageTable.get("resolved"), false));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    cq.orderBy(cb.desc(privateMessageTable.get("createdAt")));

    int perPage = Math.min(Math.abs(privateMessageReportSearchCriteria.perPage()), 20);
    int page = Math.max(privateMessageReportSearchCriteria.page() - 1, 0);

    TypedQuery<PrivateMessageReport> query = em.createQuery(cq);
    query.setMaxResults(perPage);
    query.setFirstResult(page * perPage);

    return query.getResultList();
  }
}
