package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record LikeComment(
        int comment_id,
        int score,
        String auth
) {
}