package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record ListPostLikes(
    Long post_id,
    Integer limit,
    Integer page
) {

}