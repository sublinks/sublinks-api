package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreateComment(
        String content,
        int post_id,
        int parent_id,
        int language_id,
        String form_id,
        String auth
) {
}