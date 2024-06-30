package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;

@Builder
public record PersonResponse(
    String key,
    String name,
    String displayName,
    String avatarImageUrl,
    String bannerImageUrl,
    String bio,
    String matrixUserId,
    String actorId,
    Boolean isLocal,
    Boolean isBanned,
    @Schema(description = "The date and time the users ban expires at",
        requiredMode = RequiredMode.NOT_REQUIRED) String banExpiresAt,
    Boolean isDeleted,
    Boolean isBotAccount,
    Role role,
    String createdAt,
    String updatedAt) {

}
