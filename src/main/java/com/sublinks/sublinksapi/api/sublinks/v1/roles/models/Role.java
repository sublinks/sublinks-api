package com.sublinks.sublinksapi.api.sublinks.v1.roles.models;

import lombok.Builder;

@Builder
public record Role(
    String key,
    String name,
    String description,
    Boolean isActive,
    Boolean isExpired,
    String expiresAt,
    String createdAt,
    String updatedAt) {

}
