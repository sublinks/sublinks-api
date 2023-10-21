package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record LikePost(
        Integer post_id,
        Integer score
) {
}