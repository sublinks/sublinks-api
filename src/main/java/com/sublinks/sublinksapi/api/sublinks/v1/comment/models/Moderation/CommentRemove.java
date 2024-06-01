package com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation;

public record CommentRemove(
    String reason,
    Boolean remove) {

}
