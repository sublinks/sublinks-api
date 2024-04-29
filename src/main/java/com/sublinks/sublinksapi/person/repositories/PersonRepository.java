package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findOneByName(String name);

  Optional<Person> findOneByEmail(String email);

  @Query(value = "SELECT p FROM people p WHERE p.search_vector @@ to_tsquery('keyword', :keyword)", countQuery = "SELECT COUNT(p.id) FROM people p WHERE p.search_vector @@ to_tsquery('english', :keyword)", nativeQuery = true)
  List<Person> findAllByNameAndBiography(@Param("keyword") String keyword, Pageable pageable);
}
