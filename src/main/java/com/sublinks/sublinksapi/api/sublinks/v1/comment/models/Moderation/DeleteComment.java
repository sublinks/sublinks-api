package com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation;

public record DeleteComment(
    String reason,
    Boolean remove) {

}
