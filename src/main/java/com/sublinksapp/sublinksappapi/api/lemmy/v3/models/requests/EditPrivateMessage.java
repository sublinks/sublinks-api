package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record EditPrivateMessage(
        Integer private_message_id,
        String content
) {
}