package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record EditComment(
        int comment_id,
        String content,
        int language_id,
        String form_id,
        String auth
) {
}