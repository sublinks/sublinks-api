package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record MarkPersonMentionAsRead(
        int person_mention_id,
        boolean read,
        String auth
) {
}