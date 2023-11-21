package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record CreateComment(
    String content,
    Integer post_id,
    Integer parent_id,
    Integer language_id,
    String form_id
) {

}