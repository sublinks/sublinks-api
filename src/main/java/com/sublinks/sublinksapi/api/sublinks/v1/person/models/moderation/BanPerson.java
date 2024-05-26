package com.sublinks.sublinksapi.api.sublinks.v1.person.models.moderation;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;

@Builder
public record BanPerson(
    String key,
    String reason,
    Boolean ban,
    @Schema(description = "Remove all data associated with the user",
        requiredMode = RequiredMode.NOT_REQUIRED,
        defaultValue = "false") Boolean removeData,
    @Schema(description = "Unix timestamp when the ban should expire",
        requiredMode = RequiredMode.NOT_REQUIRED) Long expirationTimestamp) {

  @Override
  public Boolean removeData() {

    return removeData != null && removeData;
  }
}
