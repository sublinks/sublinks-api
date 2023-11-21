package com.sublinks.sublinksapi.api.lemmy.v3.federatedinstance.models;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Instance;
import java.util.Collection;
import lombok.Builder;

@Builder
public record FederatedInstances(
    Collection<Instance> linked,
    Collection<Instance> allowed,
    Collection<Instance> blocked
) {

}