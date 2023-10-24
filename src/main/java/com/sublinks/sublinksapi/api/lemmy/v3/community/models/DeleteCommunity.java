package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
public record DeleteCommunity(
        Integer community_id,
        Boolean deleted
) {
}