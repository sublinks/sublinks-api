package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record BanFromCommunity(
    Integer community_id,
    Integer person_id,
    Boolean ban,
    Boolean remove_data,
    String reason,
    Integer expires
) {

}