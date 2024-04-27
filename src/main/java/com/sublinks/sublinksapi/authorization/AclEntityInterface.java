package com.sublinks.sublinksapi.authorization;

import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;

/**
 * The AclEntityInterface represents an entity that has access control list (ACL) functionality. Any
 * class that implements this interface should provide methods to manage and enforce access control
 * rules.
 */
public interface AclEntityInterface {

  Long getId();

  AuthorizedEntityType entityType();
}
