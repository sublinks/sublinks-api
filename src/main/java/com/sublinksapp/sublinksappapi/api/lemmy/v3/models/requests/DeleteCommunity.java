package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeleteCommunity(
        int community_id,
        boolean deleted,
        String auth
) {
}