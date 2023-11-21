package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ModLockPost(
    Long id,
    Long mod_person_id,
    Long post_id,
    boolean locked,
    String when_
) {

}