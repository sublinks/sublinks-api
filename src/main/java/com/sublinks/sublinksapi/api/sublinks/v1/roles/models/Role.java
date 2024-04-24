package com.sublinks.sublinksapi.api.sublinks.v1.roles.models;

import lombok.Builder;

@Builder
public record Role(String key,
                   String name,
                   String description,
                   boolean isActive,
                   boolean isExpired,
                   String expiresAt,
                   String createdAt,
                   String updatedAt) {

}
