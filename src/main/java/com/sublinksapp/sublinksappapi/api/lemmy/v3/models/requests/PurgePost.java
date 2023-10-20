package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record PurgePost(
        int post_id,
        String reason,
        String auth
) {
}