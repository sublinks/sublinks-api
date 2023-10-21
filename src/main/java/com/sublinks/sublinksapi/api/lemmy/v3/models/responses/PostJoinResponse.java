package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record PostJoinResponse(
        boolean joined
) {
}