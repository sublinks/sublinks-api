package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record MarkCommentReplyAsRead(
        int comment_reply_id,
        boolean read,
        String auth
) {
}