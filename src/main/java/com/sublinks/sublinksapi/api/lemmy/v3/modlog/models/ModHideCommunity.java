package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ModHideCommunity(
    Long id,
    Long community_id,
    Long mod_person_id,
    String when_,
    String reason,
    boolean hidden
) {

}