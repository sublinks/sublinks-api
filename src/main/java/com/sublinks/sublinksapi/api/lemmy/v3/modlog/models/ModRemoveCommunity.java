package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import lombok.Builder;

@Builder
public record ModRemoveCommunity(
    Long id,
    Long mod_person_id,
    Long community_id,
    String reason,
    boolean removed,
    String expires,
    String when_
) {

}