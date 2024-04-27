package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.PasswordReset;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

  Optional<PasswordReset> findFirstByToken(String token);

  List<PasswordReset> findAllByPersonAndUsedIsFalseAndCreatedAtAfter(Person person,
      Date createdAt);

}
