package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record FollowCommunity(
        Integer community_id,
        Boolean follow
) {
}