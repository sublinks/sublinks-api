package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreateCommentReport(
        Integer comment_id,
        String reason
) {
}