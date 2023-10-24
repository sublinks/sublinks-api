package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record DeletePost(
        Integer post_id,
        Boolean deleted
) {
}