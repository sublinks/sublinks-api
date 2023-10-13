package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeletePrivateMessage(
        int private_message_id,
        boolean deleted,
        String auth
) {
}