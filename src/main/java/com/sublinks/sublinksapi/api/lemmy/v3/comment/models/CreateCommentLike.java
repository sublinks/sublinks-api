package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record CreateCommentLike(
    Long comment_id,
    int score
) {

}
