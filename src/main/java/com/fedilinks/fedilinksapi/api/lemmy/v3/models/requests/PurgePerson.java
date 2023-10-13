package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record PurgePerson(
        int person_id,
        String reason,
        String auth
) {
}