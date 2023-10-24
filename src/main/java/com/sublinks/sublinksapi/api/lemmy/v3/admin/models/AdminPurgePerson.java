package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

@Builder
public record AdminPurgePerson(
        Long id,
        Long admin_person_id,
        String reason,
        String when_
) {
}