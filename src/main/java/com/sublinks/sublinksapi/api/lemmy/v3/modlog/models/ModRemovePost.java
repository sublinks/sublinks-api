package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import lombok.Builder;

@Builder
public record ModRemovePost(
    Long id,
    Long mod_person_id,
    Long post_id,
    String reason,
    boolean removed,
    String when_
) {

}