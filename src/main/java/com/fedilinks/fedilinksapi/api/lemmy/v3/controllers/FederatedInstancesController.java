package com.fedilinks.fedilinksapi.api.lemmy.v3.controllers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetModlogResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/federated_instances")
public class FederatedInstancesController {
    @GetMapping
    GetModlogResponse index() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
