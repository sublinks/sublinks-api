package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record AddAdmin(
        Integer person_id,
        Boolean added
) {
}