package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record HideCommunity(
        long community_id,
        Boolean hidden,
        String reason
) {
}