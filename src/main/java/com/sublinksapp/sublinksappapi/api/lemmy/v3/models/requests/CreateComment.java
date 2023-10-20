package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreateComment(
        String content,
        Integer post_id,
        Integer parent_id,
        Integer language_id,
        String form_id
) {
}