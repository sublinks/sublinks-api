package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonEmailVerification;
import com.sublinks.sublinksapi.person.dto.PersonRegistrationApplication;
import com.sublinks.sublinksapi.person.enums.PersonRegistrationApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PersonEmailVerificationRepository extends
    JpaRepository<PersonEmailVerification, Long> {

  Optional<PersonEmailVerification> findOneByPersonAndActiveIsTrue(Person person);

  Optional<PersonEmailVerification> findOneByTokenAndActiveIsTrue(String token);

}