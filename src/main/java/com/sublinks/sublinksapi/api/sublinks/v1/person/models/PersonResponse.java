package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.Role;
import java.util.Optional;
import lombok.Builder;

@Builder
public record PersonResponse(String key,
                             String name,
                             String displayName,
                             String avatarImageUrl,
                             String bannerImageUrl,
                             String bio,
                             String matrixUserId,
                             String actorId,
                             Boolean isLocal,
                             Boolean isBanned,
                             Optional<String> banExpiresAt,
                             Boolean isDeleted,
                             Boolean isBotAccount,
                             Role role,
                             String createdAt,
                             String updatedAt) {

}
