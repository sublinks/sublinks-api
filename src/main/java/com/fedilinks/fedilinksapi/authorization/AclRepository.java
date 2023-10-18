package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.authorization.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AclRepository extends JpaRepository<Acl, Long> {
    Acl findAclByPersonIdAndEntityTypeAndEntityId(Long personId, EntityType entityType, Long entityId);
}
