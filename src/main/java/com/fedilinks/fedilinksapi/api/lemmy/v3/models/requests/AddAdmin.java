package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record AddAdmin(
        int person_id,
        boolean added,
        String auth
) {
}