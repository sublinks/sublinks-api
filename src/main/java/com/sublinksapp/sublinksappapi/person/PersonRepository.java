package com.sublinksapp.sublinksappapi.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findOneByName(String name);
}
