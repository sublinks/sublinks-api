package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeletePost(
        Integer post_id,
        Boolean deleted
) {
}