package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record TransferCommunity(
        int community_id,
        int person_id,
        String auth
) {
}