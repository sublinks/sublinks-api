package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
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
    @Schema(description = "The replies to the comment.",
        requiredMode = RequiredMode.NOT_REQUIRED) List<CommentResponse> replies,
    String updatedAt) {

}
