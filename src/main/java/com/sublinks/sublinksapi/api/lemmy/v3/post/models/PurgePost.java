package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record PurgePost(
        Integer post_id,
        String reason
) {
}