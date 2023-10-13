package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record AdminPurgeComment(
        Long id,
        Long admin_person_id,
        Long post_id,
        String reason,
        String when_
) {
}