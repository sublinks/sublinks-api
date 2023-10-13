package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreatePost(
        String name,
        int community_id,
        String url,
        String body,
        String honeypot,
        boolean nsfw,
        int language_id,
        String auth
) {
}