package com.sublinks.sublinksapi.authorization;

import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;

public interface AuthorizationEntity {
    public Long getId();

    public AuthorizedEntityType entityType();
}
