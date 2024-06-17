package com.sublinks.sublinksapi.api.sublinks.v1.annoucement.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record UpdateAnnouncement(
    @Schema(description = "The new content of the announcement",
        requiredMode = RequiredMode.REQUIRED) String content) {

  public UpdateAnnouncement {

    if (content == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content_required");
    }
  }
}
