package com.sublinks.sublinksapi.api.lemmy.v3.federatedinstance.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetFederatedInstancesResponse(
    FederatedInstances federated_instances
) {

}