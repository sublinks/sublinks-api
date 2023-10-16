package com.fedilinks.fedilinksapi.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonAggregatesRepository extends JpaRepository<PersonAggregates, Long> {
    PersonAggregates findFirstByPersonId(Long personId);
}
