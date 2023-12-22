package com.sublinks.sublinksapi.api.lemmy.v3.federatedinstance.models;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.InstanceWithFederationState;
import java.util.Collection;
import lombok.Builder;

@Builder
public record FederatedInstances(
    Collection<InstanceWithFederationState> linked,
    Collection<InstanceWithFederationState> allowed,
    Collection<InstanceWithFederationState> blocked
) {

}