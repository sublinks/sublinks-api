package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record LikeComment(
        Integer comment_id,
        Integer score
) {
}