package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.PersonAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonAggregateRepository extends JpaRepository<PersonAggregate, Long> {

  PersonAggregate findFirstByPersonId(Long personId);
}
