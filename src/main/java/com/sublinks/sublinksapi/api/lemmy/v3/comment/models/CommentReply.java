package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CommentReply(
    Long id,
    Long recipient_id,
    Long comment_id,
    boolean read,
    String published
) {

}