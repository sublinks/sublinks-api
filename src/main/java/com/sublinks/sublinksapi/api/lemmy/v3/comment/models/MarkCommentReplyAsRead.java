package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record MarkCommentReplyAsRead(
    Integer comment_reply_id,
    Boolean read
) {

}