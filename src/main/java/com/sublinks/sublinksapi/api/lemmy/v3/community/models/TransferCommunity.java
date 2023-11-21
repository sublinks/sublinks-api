package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record TransferCommunity(
    Integer community_id,
    Integer person_id
) {

}