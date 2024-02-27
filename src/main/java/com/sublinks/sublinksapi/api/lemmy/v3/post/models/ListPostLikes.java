package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record ListPostLikes(
    Integer post_id,
    Integer limit,
    Integer page
) {

}