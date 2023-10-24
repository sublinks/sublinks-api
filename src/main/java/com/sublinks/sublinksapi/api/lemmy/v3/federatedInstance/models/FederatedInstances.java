package com.sublinks.sublinksapi.api.lemmy.v3.federatedInstance.models;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Instance;
import lombok.Builder;

import java.util.Collection;

@Builder
public record FederatedInstances(
        Collection<Instance> linked,
        Collection<Instance> allowed,
        Collection<Instance> blocked
) {
}