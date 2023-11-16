package com.sublinks.sublinksapi.api.lemmy.v3.search.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.GetModlogResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.search.models.Search;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/search")
@Tag(name = "search", description = "the search API")
public class SearchController {
    @GetMapping
    GetModlogResponse index(@Valid final Search searchForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
