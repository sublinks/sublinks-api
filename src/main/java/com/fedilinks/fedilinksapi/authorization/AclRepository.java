package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.authorization.enums.AuthorizedAction;
import com.fedilinks.fedilinksapi.authorization.enums.AuthorizedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AclRepository extends JpaRepository<Acl, Long> {

    boolean existsByPersonIdAndEntityTypeAndAuthorizedAction(Long personId, AuthorizedEntityType entityType, AuthorizedAction authorizedAction);

    boolean existsByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(Long personId, AuthorizedEntityType entityType, Long entityId, AuthorizedAction authorizedAction);

    Acl findAclByPersonIdAndEntityTypeAndAuthorizedAction(Long personId, AuthorizedEntityType entityType, AuthorizedAction authorizedAction);
    Acl findAclByPersonIdAndEntityTypeAndAuthorizedActionAndPermitted(Long personId, AuthorizedEntityType entityType, AuthorizedAction authorizedAction, boolean isPermitted);

    Acl findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(Long personId, AuthorizedEntityType entityType, Long entityId, AuthorizedAction authorizedAction);
    Acl findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(Long personId, AuthorizedEntityType entityType, Long entityId, AuthorizedAction authorizedAction, boolean isPermitted);
}
