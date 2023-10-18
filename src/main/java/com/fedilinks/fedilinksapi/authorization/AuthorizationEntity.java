package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.authorization.enums.AuthorizedEntityType;

public interface AuthorizationEntity {
    public Long getId();

    public AuthorizedEntityType entityType();
}
