package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record MarkPostAsRead(
        Integer post_id,
        Boolean read
) {
}