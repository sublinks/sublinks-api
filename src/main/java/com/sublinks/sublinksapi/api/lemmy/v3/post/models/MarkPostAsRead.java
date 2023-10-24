package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record MarkPostAsRead(
        Integer post_id,
        Boolean read
) {
}