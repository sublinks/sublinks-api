package com.sublinks.sublinksapi.api.lemmy.v3.resolveObject;

import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.ResolveObject;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetModlogResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/resolve_object")
public class ResolveObjectController {
    @GetMapping
    GetModlogResponse index(@Valid ResolveObject resolveObjectForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
