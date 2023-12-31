package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findOneByName(String name);
}
