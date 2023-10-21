package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

import java.util.Collection;

@Builder
public record FederatedInstances(
        Collection<Instance> linked,
        Collection<Instance> allowed,
        Collection<Instance> blocked
) {
}