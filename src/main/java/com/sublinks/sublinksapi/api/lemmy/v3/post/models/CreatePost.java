package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

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