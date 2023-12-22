package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import java.util.Date;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record InstanceWithFederationState(
    Long id,
    String domain,
    Date published,
    Date updated,
    String software,
    String version,
    ReadableFederationState federation_state
) {

}