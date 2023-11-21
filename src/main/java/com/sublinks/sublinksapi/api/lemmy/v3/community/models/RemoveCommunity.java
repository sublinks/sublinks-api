package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record RemoveCommunity(
    Integer community_id,
    Boolean removed,
    String reason,
    Integer expires
) {

}