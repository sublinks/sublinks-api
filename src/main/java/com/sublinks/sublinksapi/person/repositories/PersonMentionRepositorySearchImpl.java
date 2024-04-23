package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.PersonMention;
import com.sublinks.sublinksapi.person.models.PersonMentionSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

@AllArgsConstructor
public class PersonMentionRepositorySearchImpl implements PersonMentionRepositorySearch {

  private final EntityManager em;

  @Override
  public List<PersonMention> allPersonMentionBySearchCriteria(
      PersonMentionSearchCriteria personMentionSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<PersonMention> cq = cb.createQuery(PersonMention.class);

    final Root<PersonMention> personMentionTable = cq.from(PersonMention.class);

    final List<Predicate> predicates = new ArrayList<>();

    // Join for CommentView
    if (!personMentionSearchCriteria.unreadOnly()) {
      predicates.add(cb.equal(personMentionTable.get("isRead"), false));
    }
    cq.where(predicates.toArray(new Predicate[0]));

    switch (personMentionSearchCriteria.sort()) {
      case New:
        cq.orderBy(cb.desc(personMentionTable.get("createdAt")));
        break;
      case Old:
        cq.orderBy(cb.asc(personMentionTable.get("createdAt")));
        break;
      default:
        cq.orderBy(cb.desc(personMentionTable.get("createdAt")));
        break;
    }

    int perPage = Math.min(Math.abs(personMentionSearchCriteria.perPage()), 20);

    TypedQuery<PersonMention> query = em.createQuery(cq);

    applyPagination(query, personMentionSearchCriteria.page(), perPage);

    return query.getResultList();
  }
}
