package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record ModRemoveComment(
        Long id,
        Long mod_person_id,
        Long comment_id,
        String reason,
        boolean removed,
        String when_
) {
}