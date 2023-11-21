package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record BlockCommunity(
    Long community_id,
    Boolean block
) {

}