package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record LockPost(
        Integer post_id,
        Boolean locked
) {
}