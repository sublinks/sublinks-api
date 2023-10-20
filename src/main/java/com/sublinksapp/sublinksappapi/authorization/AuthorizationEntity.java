package com.sublinksapp.sublinksappapi.authorization;

import com.sublinksapp.sublinksappapi.authorization.enums.AuthorizedEntityType;

public interface AuthorizationEntity {
    public Long getId();

    public AuthorizedEntityType entityType();
}
