package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record ListCommentLikes(
    Integer comment_id,
    Integer limit,
    Integer page
) {

}