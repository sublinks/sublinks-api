package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.FederatedInstances;
import lombok.Builder;

@Builder
public record GetFederatedInstancesResponse(
        FederatedInstances federated_instances
) {
}