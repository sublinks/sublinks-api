package com.sublinks.sublinksapi.post.models;

import lombok.Builder;

@Builder
public record PostLikeSearchCriteria(
    Long postId,
    int perPage,
    int page
) {

}
