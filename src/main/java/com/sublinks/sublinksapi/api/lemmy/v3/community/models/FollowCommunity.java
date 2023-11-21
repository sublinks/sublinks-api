package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record FollowCommunity(
    Integer community_id,
    Boolean follow
) {

}