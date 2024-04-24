package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.Role;
import java.util.List;
import java.util.Optional;
import lombok.Builder;

@Builder
public record PersonResponse(String key,
                             String name,
                             String displayNname,
                             String avatarImageUrl,
                             String bannerImageUrl,
                             String bio,
                             String matrixUserId,
                             String actorId,
                             boolean isLocal,
                             boolean isBanned,
                             Optional<String> banExpiresAt,
                             boolean isDeleted,
                             boolean isBotAccount,
                             Role role,
                             List<Langauges> languages,
                             String createdAt,
                             String updatedAt) {

}
