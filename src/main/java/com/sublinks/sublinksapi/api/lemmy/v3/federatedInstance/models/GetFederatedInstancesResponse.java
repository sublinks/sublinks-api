package com.sublinks.sublinksapi.api.lemmy.v3.federatedInstance.models;

import lombok.Builder;

@Builder
public record GetFederatedInstancesResponse(
        FederatedInstances federated_instances
) {
}