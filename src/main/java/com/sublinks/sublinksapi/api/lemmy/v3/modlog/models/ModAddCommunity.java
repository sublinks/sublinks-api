package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ModAddCommunity(
    Long id,
    Long mod_person_id,
    Long other_person_id,
    Long community_id,
    boolean removed,
    String when_
) {

}