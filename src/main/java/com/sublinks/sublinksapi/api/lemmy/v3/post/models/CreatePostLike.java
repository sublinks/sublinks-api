package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record CreatePostLike(
    Long post_id,
    Integer score
) {

}