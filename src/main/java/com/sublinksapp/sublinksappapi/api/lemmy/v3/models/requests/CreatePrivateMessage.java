package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreatePrivateMessage(
        String content,
        Integer recipient_id
) {
}