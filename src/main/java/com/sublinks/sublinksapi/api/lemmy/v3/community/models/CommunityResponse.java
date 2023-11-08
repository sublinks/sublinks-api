package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

import java.util.List;

@Builder
public record CommunityResponse(
        CommunityView community_view,
        List<Long> discussion_languages
) {
}