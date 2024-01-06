package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ReadableFederationState(
    Long instance_id,
    String last_successful_id,
    String last_successful_published_time,
    Integer final_count,
    String last_retry,
    String next_retry
) {

}
