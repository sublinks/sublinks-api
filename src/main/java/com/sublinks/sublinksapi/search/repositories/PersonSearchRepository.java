package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonSearchRepository extends JpaRepository<Person, Long> {

  @Query(value = "SELECT p.*, lpi.instance_id FROM people p LEFT JOIN link_person_instances lpi ON lpi.person_id = p.id WHERE p.display_name_search @@ to_tsquery(:keyword) OR p.name_search @@ to_tsquery(:keyword) OR p.biography_search @@ to_tsquery(:keyword);", countQuery = "SELECT COUNT(p.id) FROM people p WHERE p.display_name_search @@ to_tsquery(:keyword) OR p.name_search @@ to_tsquery(:keyword) OR p.biography_search @@ to_tsquery(:keyword);", nativeQuery = true)
  Page<Person> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
