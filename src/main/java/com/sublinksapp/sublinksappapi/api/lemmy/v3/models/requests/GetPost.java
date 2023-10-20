package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetPost(
        int id,
        int comment_id,
        String auth
) {
}