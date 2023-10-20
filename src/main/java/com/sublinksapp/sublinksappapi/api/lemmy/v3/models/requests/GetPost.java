package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetPost(
        Integer id,
        Integer comment_id
) {
}