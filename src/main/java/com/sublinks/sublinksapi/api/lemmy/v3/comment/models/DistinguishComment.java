package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record DistinguishComment(
    Integer comment_id,
    Boolean distinguished
) {

}