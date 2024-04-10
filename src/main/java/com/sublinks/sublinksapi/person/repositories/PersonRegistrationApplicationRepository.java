package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import com.sublinks.sublinksapi.person.enums.PersonRegistrationApplicationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRegistrationApplicationRepository extends
    JpaRepository<PersonRegistrationApplication, Long> {


  Optional<PersonRegistrationApplication> findOneByPerson(Person person);


  List<PersonRegistrationApplication> findAllByApplicationStatus(
      PersonRegistrationApplicationStatus status);

  long countByApplicationStatus(PersonRegistrationApplicationStatus status);
}
