package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record MarkPrivateMessageAsRead(
        Integer private_message_id,
        Boolean read
) {
}