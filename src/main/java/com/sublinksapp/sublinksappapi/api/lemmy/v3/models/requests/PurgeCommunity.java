package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record PurgeCommunity(
        Integer community_id,
        String reason
) {
}