package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record AddModToCommunity(
        int community_id,
        int person_id,
        boolean added,
        String auth
) {
}