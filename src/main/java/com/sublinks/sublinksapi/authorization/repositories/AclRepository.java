package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.dto.Acl;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;

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
