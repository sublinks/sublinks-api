package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record TransferCommunity(
        Integer community_id,
        Integer person_id
) {
}