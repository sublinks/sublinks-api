package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import java.util.Collection;
import java.util.Set;

public interface RoleSearchRepository {

  boolean existsByPersonIdAndRolePermissionWithin(Long id, Set<RolePermission> rolePermissions);

  boolean existsByPersonIdAndRolePermissionContains(Long id, RolePermission rolePermission);

  Collection<Role> findAllByRolePermissionWithin(Set<RolePermission> rolePermissions);

  Collection<Role> findAllByRolePermissionIsIn(Set<RolePermission> rolePermissions);

  Collection<Role> findAllByRolePermissionContains(RolePermission rolePermission);
}
