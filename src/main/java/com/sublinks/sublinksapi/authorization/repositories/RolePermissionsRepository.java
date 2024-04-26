package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.entities.RolePermissions;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Long> {

  Collection<RolePermissions> findByRoleId(Long roleId);

  Collection<RolePermissions> findByRoleIdIn(Collection<Long> roleIds);

  Collection<RolePermissions> findAllByPermission(RolePermission permission);
}
