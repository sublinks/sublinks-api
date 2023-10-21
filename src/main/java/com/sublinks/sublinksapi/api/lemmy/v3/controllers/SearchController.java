package com.sublinks.sublinksapi.api.lemmy.v3.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.Search;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetModlogResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/search")
public class SearchController {
    @GetMapping
    GetModlogResponse index(@Valid Search searchForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
