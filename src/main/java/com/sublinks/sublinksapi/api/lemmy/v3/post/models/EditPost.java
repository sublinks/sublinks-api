package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record EditPost(
    Integer post_id,
    String name,
    String url,
    String body,
    Boolean nsfw,
    Integer language_id
) {

}