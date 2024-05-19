package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;

@Builder
public record UpdateComment(String commentKey,
                            String body,
                            @Schema(description = "The language key of the comment", defaultValue = "und", example = "und", requiredMode = RequiredMode.NOT_REQUIRED) String languageKey,
                            @Schema(description = "Whether the comment is featured ( Requires Moderator or Admin )", defaultValue = "false", example = "false", requiredMode = RequiredMode.NOT_REQUIRED) Boolean featured) {

}
