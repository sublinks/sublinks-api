package com.sublinksapp.sublinksappapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record AdminPurgeCommunity(
        Long id,
        Long admin_person_id,
        String reason,
        String when_
) {
}