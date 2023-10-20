package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record MarkPostAsRead(
        int post_id,
        boolean read,
        String auth
) {
}