package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CommentReport(
    Long id,
    Long creator_id,
    Long comment_id,
    String original_comment_text,
    String reason,
    boolean resolved,
    Long resolver_id,
    String published,
    String updated
) {

}