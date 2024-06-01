package com.sublinks.sublinksapi.api.sublinks.v1.post.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Builder
public record CreatePost(
    String title,
    String body,
    @Schema(description = "The language key of the comment",
        defaultValue = "und",
        example = "und",
        requiredMode = RequiredMode.NOT_REQUIRED) String languageKey,
    @Schema(description = "Whether the comment is featured ( Requires permission to do so. )",
        defaultValue = "false",
        example = "false",
        requiredMode = RequiredMode.NOT_REQUIRED) Boolean featured) {

  public CreatePost {

    if (title.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title_can_not_be_blank");
    }
    if (body.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "body_can_not_be_blank");
    }
  }

  public String languageKey() {

    return languageKey == null ? "und" : languageKey;
  }
}
