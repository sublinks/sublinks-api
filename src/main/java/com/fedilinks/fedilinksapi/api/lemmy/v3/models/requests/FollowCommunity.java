package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record FollowCommunity(
        int community_id,
        boolean follow,
        String auth
) {
}