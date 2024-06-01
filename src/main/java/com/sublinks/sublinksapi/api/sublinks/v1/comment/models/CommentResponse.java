package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import lombok.Builder;

@Builder
public record CommentResponse(
    String key,
    String activityPubId,
    String body,
    String path,
    Boolean isLocal,
    Boolean isDeleted,
    Boolean isFeatured,
    Boolean isRemoved,
    String createdAt,
    PersonResponse creator,
    String updatedAt) {

}
