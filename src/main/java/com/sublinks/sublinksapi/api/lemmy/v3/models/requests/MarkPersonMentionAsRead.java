package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record MarkPersonMentionAsRead(
        Integer person_mention_id,
        Boolean read
) {
}