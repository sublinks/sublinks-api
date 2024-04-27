package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonEmailVerificationRepository extends
    JpaRepository<PersonEmailVerification, Long> {

  Optional<PersonEmailVerification> findOneByPersonAndActiveIsTrue(Person person);

  Optional<PersonEmailVerification> findOneByTokenAndActiveIsTrue(String token);

}