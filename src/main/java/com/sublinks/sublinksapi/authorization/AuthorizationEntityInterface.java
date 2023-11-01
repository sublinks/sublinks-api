package com.sublinks.sublinksapi.authorization;

import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;

public interface AuthorizationEntityInterface {
    Long getId();

    AuthorizedEntityType entityType();
}
