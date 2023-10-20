package com.sublinksapp.sublinksappapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record AdminPurgePost(
        Long id,
        Long admin_person_id,
        Long community_id,
        String reason,
        String when_
) {
}