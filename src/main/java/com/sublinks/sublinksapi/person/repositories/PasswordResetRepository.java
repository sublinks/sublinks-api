package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.dto.PasswordReset;
import com.sublinks.sublinksapi.person.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

  Optional<PasswordReset> findFirstByToken(String token);

  List<PasswordReset> findAllByPersonAndUsedIsFalseAndCreatedAtAfter(Person person,
      Date createdAt);

}
