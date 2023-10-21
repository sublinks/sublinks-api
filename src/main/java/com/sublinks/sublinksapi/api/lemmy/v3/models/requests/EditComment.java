package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record EditComment(
        Integer comment_id,
        String content,
        Integer language_id,
        String form_id
) {
}