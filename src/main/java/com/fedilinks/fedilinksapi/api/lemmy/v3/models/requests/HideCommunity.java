package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record HideCommunity(
        long community_id,
        boolean hidden,
        String reason
) {
}