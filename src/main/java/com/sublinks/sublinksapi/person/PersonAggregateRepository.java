package com.sublinks.sublinksapi.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonAggregateRepository extends JpaRepository<PersonAggregate, Long> {
    PersonAggregate findFirstByPersonId(Long personId);
}
