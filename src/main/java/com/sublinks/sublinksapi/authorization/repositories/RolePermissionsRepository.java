package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.dto.RolePermissions;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Long> {

    Collection<RolePermissions> findByRoleId(Long roleId);

    Collection<RolePermissions> findByRoleIdIn(Collection<Long> roleIds);

    Collection<RolePermissions> findAllByPermission(RolePermission permission);
}
