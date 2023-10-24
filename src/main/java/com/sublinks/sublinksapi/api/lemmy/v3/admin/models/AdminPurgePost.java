package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

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