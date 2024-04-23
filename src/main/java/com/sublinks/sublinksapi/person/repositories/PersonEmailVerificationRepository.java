package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonEmailVerificationRepository extends
    JpaRepository<PersonEmailVerification, Long> {

  Optional<PersonEmailVerification> findOneByPersonAndActiveIsTrue(Person person);

  Optional<PersonEmailVerification> findOneByTokenAndActiveIsTrue(String token);

}