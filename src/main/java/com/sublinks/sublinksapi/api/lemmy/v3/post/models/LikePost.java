package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record LikePost(
        Integer post_id,
        Integer score
) {
}