package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record SaveComment(
        int comment_id,
        boolean save,
        String auth
) {
}