package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record GetUnreadCountResponse(
        int replies,
        int mentions,
        int private_messages
) {
}