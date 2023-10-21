package com.sublinks.sublinksapi.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonAggregatesRepository extends JpaRepository<PersonAggregates, Long> {
    PersonAggregates findFirstByPersonId(Long personId);
}
