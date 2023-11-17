package com.sublinks.sublinksapi.api.lemmy.v3.common.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.person.dto.Person;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class AbstractLemmyApiController {

    public Person getPersonOrThrowBadRequest(JwtPerson principal) throws ResponseStatusException {

        return Optional.ofNullable(principal).map(p -> (Person) p.getPrincipal()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST)
        );
    }

    public Person getPersonOrThrowUnauthorized(JwtPerson principal) throws ResponseStatusException {

        return Optional.ofNullable(principal).map(p -> (Person) p.getPrincipal()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        );
    }

    public Person getPerson(JwtPerson principal) {

        return getOptionalPerson(principal).orElseGet(() -> null);
    }

    public Optional<Person> getOptionalPerson(JwtPerson principal) {

        return Optional.ofNullable(principal).map(p -> (Person) p.getPrincipal());
    }
}
