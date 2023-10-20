package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeleteComment(
        Integer comment_id,
        Boolean deleted
) {
}