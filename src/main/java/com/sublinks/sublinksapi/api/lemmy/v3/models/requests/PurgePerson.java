package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record PurgePerson(
        Integer person_id,
        String reason
) {
}