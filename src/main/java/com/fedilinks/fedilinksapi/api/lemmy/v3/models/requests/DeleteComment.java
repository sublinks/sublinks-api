package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeleteComment(
        int comment_id,
        boolean deleted,
        String auth
) {
}