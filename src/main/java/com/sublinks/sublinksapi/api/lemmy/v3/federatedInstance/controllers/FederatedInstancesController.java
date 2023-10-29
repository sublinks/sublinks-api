package com.sublinks.sublinksapi.api.lemmy.v3.federatedInstance.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.federatedInstance.models.GetFederatedInstancesResponse;
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
