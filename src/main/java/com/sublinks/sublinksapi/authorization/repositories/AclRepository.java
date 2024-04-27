package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.entities.Acl;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The AclRepository interface provides methods for accessing and manipulating the Access Control
 * List (ACL) entities. ACLs are used to specify access permissions for specific entities and
 * authorized actions for individual persons.
 */
public interface AclRepository extends JpaRepository<Acl, Long> {

  boolean existsByPersonIdAndEntityTypeAndAuthorizedAction(Long personId,
      AuthorizedEntityType entityType, AuthorizeAction authorizedAction);

  boolean existsByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(Long personId,
      AuthorizedEntityType entityType, Long entityId, AuthorizeAction authorizedAction);

  Acl findAclByPersonIdAndEntityTypeAndAuthorizedAction(Long personId,
      AuthorizedEntityType entityType, AuthorizeAction authorizedAction);

  Acl findAclByPersonIdAndEntityTypeAndAuthorizedActionAndPermitted(Long personId,
      AuthorizedEntityType entityType, AuthorizeAction authorizedAction, boolean isPermitted);

  Acl findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(Long personId,
      AuthorizedEntityType entityType, Long entityId, AuthorizeAction authorizedAction);

  Acl findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(Long personId,
      AuthorizedEntityType entityType, Long entityId, AuthorizeAction authorizedAction,
      boolean isPermitted);
}
