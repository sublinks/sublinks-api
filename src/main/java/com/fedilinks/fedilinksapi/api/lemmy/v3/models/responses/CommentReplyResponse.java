package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommentReplyView;
import lombok.Builder;

@Builder
public record CommentReplyResponse(
        CommentReplyView comment_reply_view
) {
}