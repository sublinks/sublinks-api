package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record AddModToCommunity(
    Integer community_id,
    Integer person_id,
    Boolean added
) {

}