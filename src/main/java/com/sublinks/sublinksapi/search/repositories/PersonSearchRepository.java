package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonSearchRepository extends JpaRepository<Person, Long> {

   @Query(value = "SELECT p.* FROM people p  WHERE MATCH(p.name, p.display_name) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
           nativeQuery = true)
    Page<Person> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
