package com.sublinks.sublinksapi.api.lemmy.v3.resolveObject.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.GetModlogResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.resolveObject.models.ResolveObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/resolve_object")
@Tag(name = "resolve_object", description = "the resolve object API")
public class ResolveObjectController {
    @GetMapping
    GetModlogResponse index(@Valid final ResolveObject resolveObjectForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
