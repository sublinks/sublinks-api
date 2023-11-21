package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record HideCommunity(
    long community_id,
    Boolean hidden,
    String reason
) {

}