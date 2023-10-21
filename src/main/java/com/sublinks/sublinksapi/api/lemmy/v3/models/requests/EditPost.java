package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record EditPost(
        Integer post_id,
        String name,
        String url,
        String body,
        Boolean nsfw,
        Integer language_id
) {
}