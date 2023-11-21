package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record MarkCommentReplyAsRead(
    Integer comment_reply_id,
    Boolean read
) {

}