package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
@Schema(description = "The GetPost model, either the post ID or the comment ID is required.")
public record GetPost(
    @Schema(description = "The post ID", requiredMode = RequiredMode.NOT_REQUIRED)
    Integer id,
    @Schema(description = "The comment ID", requiredMode = RequiredMode.NOT_REQUIRED)
    Integer comment_id
) {

}