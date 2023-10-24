package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

import java.util.List;

@Builder
public record AddModToCommunityResponse(
        List<CommunityModeratorView> moderators
) {
}