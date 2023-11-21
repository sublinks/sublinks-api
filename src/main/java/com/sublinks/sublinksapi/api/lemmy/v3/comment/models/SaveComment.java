package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record SaveComment(
    Integer comment_id,
    Boolean save
) {

}