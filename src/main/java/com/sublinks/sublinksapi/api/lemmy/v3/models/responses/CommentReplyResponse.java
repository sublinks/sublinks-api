package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommentReplyView;
import lombok.Builder;

@Builder
public record CommentReplyResponse(
        CommentReplyView comment_reply_view
) {
}