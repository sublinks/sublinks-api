package com.fedilinks.fedilinksapi.api.lemmy.v3.controllers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetFederatedInstancesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v3/federated_instances")
public class FederatedInstancesController {
    @GetMapping
    GetFederatedInstancesResponse index() {
        return GetFederatedInstancesResponse
                .builder()
                .build();
    }
}
