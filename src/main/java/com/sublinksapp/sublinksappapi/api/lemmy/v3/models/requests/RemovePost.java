package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record RemovePost(
        int post_id,
        boolean removed,
        String reason,
        String auth
) {
}