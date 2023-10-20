package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreatePost(
        String name,
        Integer community_id,
        String url,
        String body,
        String honeypot,
        Boolean nsfw,
        Integer language_id
) {
}