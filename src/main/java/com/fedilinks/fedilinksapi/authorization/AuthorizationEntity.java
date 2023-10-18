package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.authorization.enums.EntityType;

public interface AuthorizationEntity {
    public Long getId();

    public EntityType entityType();
}
