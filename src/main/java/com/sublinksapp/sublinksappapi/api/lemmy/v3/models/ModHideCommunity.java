package com.sublinksapp.sublinksappapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record ModHideCommunity(
        Long id,
        Long community_id,
        Long mod_person_id,
        String when_,
        String reason,
        boolean hidden
) {
}