package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record AddModToCommunity(
        Integer community_id,
        Integer person_id,
        Boolean added
) {
}