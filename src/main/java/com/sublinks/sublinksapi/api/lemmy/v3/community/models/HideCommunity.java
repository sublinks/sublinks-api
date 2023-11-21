package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
public record HideCommunity(
    long community_id,
    Boolean hidden,
    String reason
) {

}