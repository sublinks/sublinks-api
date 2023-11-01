package com.sublinks.sublinksapi.authorization;

import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;

public interface AuthorizationEntity {
    Long getId();

    AuthorizedEntityType entityType();
}
