package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record LikeComment(
        Integer comment_id,
        Integer score
) {
}