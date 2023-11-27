package com.sublinks.sublinksapi.api.lemmy.v3.common.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.PersonRegistrationApplicationStatus;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Base controller for Lemmy api controllers. Contains repeated functions for example authentication
 * checks
 */
public abstract class AbstractLemmyApiController {

  /**
   * Get the person object or throw a 400 Bad Request exception.
   *
   * @param principal JwtPerson object that contains the person as it's principal
   * @return Person
   * @throws ResponseStatusException Exception thrown when Person not present
   */
  public Person getPersonOrThrowBadRequest(JwtPerson principal) throws ResponseStatusException {

    return Optional.ofNullable(principal).map(p -> (Person) p.getPrincipal())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
  }

  /**
   * Get the person object or throw a 401 Unauthorized exception.
   *
   * @param principal JwtPerson object that contains the person as it's principal
   * @return Person
   * @throws ResponseStatusException Exception thrown when Person not present
   */
  public Person getPersonOrThrowUnauthorized(JwtPerson principal) throws ResponseStatusException {

    return Optional.ofNullable(principal).map(p -> (Person) p.getPrincipal()).filter(
            p -> p.getRegistrationApplication() == null
                || p.getRegistrationApplication().getApplicationStatus()
                == PersonRegistrationApplicationStatus.approved)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
  }

  public Person getPerson(JwtPerson principal) {

    return getOptionalPerson(principal).orElseGet(() -> null);
  }

  public Optional<Person> getOptionalPerson(JwtPerson principal) {

    return Optional.ofNullable(principal).map(p -> (Person) p.getPrincipal());
  }
}
