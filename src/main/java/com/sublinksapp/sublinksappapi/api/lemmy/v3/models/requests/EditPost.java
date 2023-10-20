package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record EditPost(
        int post_id,
        String name,
        String url,
        String body,
        boolean nsfw,
        int language_id,
        String auth
) {
}