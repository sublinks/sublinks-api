package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record DeleteComment(
        Integer comment_id,
        Boolean deleted
) {
}