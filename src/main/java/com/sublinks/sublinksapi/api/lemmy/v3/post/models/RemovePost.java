package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record RemovePost(
    Integer post_id,
    Boolean removed,
    String reason
) {

}