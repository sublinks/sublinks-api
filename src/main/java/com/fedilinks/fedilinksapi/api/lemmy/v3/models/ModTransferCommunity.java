package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record ModTransferCommunity(
        Long id,
        Long mod_person_id,
        Long other_person_id,
        Long community_id,
        String when_
) {
}