package com.sublinks.sublinksapi.api.lemmy.v3.modlog.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.GetModLog;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.GetModlogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sublinks.sublinksapi.person.dto.Person;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v3/modlog")
@Tag(name = "modlog", description = "the modlog API")
public class ModlogController extends AbstractLemmyApiController {
    @GetMapping
    GetModlogResponse index(@Valid final GetModLog getModLogForm, JwtPerson principal) {

        Optional<Person> person = getOptionalPerson(principal);

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
