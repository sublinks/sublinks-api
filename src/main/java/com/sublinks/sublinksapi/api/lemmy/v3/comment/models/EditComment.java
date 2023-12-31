package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record EditComment(
    Integer comment_id,
    String content,
    Integer language_id
) {

}