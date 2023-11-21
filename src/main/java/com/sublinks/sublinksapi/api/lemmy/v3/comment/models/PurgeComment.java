package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PurgeComment(
    Integer comment_id,
    String reason
) {

}