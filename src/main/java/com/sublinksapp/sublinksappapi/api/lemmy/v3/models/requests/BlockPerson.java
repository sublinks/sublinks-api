package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record BlockPerson(
        int person_id,
        boolean block,
        String auth
) {
}