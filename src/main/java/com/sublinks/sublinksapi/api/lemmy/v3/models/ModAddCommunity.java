package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record ModAddCommunity(
        Long id,
        Long mod_person_id,
        Long other_person_id,
        Long community_id,
        boolean removed,
        String when_
) {
}