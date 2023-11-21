package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
public record BlockCommunity(
    Long community_id,
    Boolean block
) {

}