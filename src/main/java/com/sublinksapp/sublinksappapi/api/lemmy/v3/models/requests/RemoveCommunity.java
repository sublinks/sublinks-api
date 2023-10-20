package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record RemoveCommunity(
        int community_id,
        boolean removed,
        String reason,
        int expires,
        String auth
) {
}