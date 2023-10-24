package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record LockPost(
        Integer post_id,
        Boolean locked
) {
}