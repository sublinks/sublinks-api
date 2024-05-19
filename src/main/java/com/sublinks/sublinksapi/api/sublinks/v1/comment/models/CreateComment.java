package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;

@Builder
public record CreateComment(String body,
                            @Schema(description = "The parent key of the comment", requiredMode = RequiredMode.NOT_REQUIRED) String parentKey,
                            String postKey,
                            @Schema(description = "The language key of the comment", defaultValue = "und", example = "und", requiredMode = RequiredMode.NOT_REQUIRED) String languageKey,
                            @Schema(description = "Whether the comment is featured ( Requires permission to do so. )", defaultValue = "false", example = "false", requiredMode = RequiredMode.NOT_REQUIRED) Boolean featured) {

}
