package com.sublinks.sublinksapi.comment.models;

import lombok.Builder;

@Builder
public record CommentLikeSearchCriteria(
    Long commentId,
    int perPage,
    int page
) {

}
