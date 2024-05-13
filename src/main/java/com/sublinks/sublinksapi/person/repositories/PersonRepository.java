package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The PersonRepository interface is a repository interface that extends the JpaRepository
 * interface. It provides methods for querying and manipulating Person objects in the database.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findOneByNameIgnoreCase(String name);

  Optional<Person> findOneByEmail(String email);

  HashSet<Person> findAllByRole(Role role);

  List<Person> findAllByRoleExpireAtBefore(Date expireAt);
}
