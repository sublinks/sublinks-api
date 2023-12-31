package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record BlockCommunityResponse(
    CommunityView community_view,
    boolean blocked
) {

}