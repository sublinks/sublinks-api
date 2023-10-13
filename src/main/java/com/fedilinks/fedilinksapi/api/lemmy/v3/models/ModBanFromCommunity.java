package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record ModBanFromCommunity(
        Long id,
        Long mod_person_id,
        Long other_person_id,
        Long community_id,
        String reason,
        boolean banned,
        String expires,
        String when_
) {
}