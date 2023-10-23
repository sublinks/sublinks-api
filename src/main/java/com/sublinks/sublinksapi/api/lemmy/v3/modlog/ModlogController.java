package com.sublinks.sublinksapi.api.lemmy.v3.modlog;

import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.GetModLog;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetModlogResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/modlog")
public class ModlogController {
    @GetMapping
    GetModlogResponse index(@Valid GetModLog getModLogForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
