package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record ModLockPost(
        Long id,
        Long mod_person_id,
        Long post_id,
        boolean locked,
        String when_
) {
}