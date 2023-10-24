package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

import java.util.Collection;

@Builder
public record ListCommunitiesResponse(
        Collection<CommunityView> communities
) {
}