package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record PurgeComment(
        Integer comment_id,
        String reason
) {
}