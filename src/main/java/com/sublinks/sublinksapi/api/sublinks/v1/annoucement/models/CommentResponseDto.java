package com.sublinks.sublinksapi.api.sublinks.v1.annoucement.models;

public record CommentResponseDto(
    String id,
    String postId,
    String personId,
    String communityId,
    String content,
    String createdAt,
    String updatedAt
) {

}
