package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.entities.Acl;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The AclRepository interface provides methods for accessing and manipulating the Access Control
 * List (ACL) entities. ACLs are used to specify access permissions for specific entities and
 * authorized actions for individual persons.
 */
public interface AclRepository extends JpaRepository<Acl, Long> {

  Acl findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(Long personId,
      AuthorizedEntityType entityType, Long entityId, String authorizedAction);

  Acl findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(Long personId,
      AuthorizedEntityType entityType, Long entityId, String authorizedAction,
      boolean isPermitted);
}
