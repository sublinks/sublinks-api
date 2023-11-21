package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record BlockInstance(
    Integer instance_id,
    Boolean block
) {

}