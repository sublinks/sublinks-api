package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record RemoveCommunity(
        Integer community_id,
        Boolean removed,
        String reason,
        Integer expires
) {
}