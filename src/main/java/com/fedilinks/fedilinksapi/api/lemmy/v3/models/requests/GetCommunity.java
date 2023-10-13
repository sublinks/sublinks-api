package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetCommunity(
        int id,
        String name,
        String auth
) {
}