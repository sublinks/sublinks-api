package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

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