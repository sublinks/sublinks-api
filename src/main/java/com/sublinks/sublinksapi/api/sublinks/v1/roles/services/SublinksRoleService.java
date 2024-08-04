package com.sublinks.sublinksapi.api.sublinks.v1.roles.services;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortOrder;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.CreateRole;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.IndexRole;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.RoleResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.UpdateRole;
import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.entities.RolePermissions;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionRoleTypes;
import com.sublinks.sublinksapi.authorization.repositories.RoleRepository;
import com.sublinks.sublinksapi.authorization.services.AclService;
import com.sublinks.sublinksapi.authorization.services.RoleService;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class SublinksRoleService {

  private final ConversionService conversionService;
  private final AclService aclService;
  private final RoleRepository roleRepository;
  private final RoleService roleService;

  public List<RoleResponse> indexRole(final IndexRole indexRoleForm, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionRoleTypes.ROLE_READ)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized"));

    return roleRepository.findAllByNameIsLikeIgnoreCase(indexRoleForm.search(),
            PageRequest.of(indexRoleForm.page(), indexRoleForm.limit(), indexRoleForm.sort()
                .equals(SortOrder.Asc) ? Sort.by("name")
                .ascending() : Sort.by("name")
                .descending()))
        .stream()
        .map(role -> conversionService.convert(role, RoleResponse.class))
        .toList();
  }

  public RoleResponse show(final String key, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionRoleTypes.ROLE_READ)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized"));

    return roleRepository.findFirstByName(key)
        .map(role -> conversionService.convert(role, RoleResponse.class))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "role not found"));
  }

  public RoleResponse create(final CreateRole createRoleForm, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionRoleTypes.ROLE_CREATE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized"));

    final Role role = Role.builder()
        .name(createRoleForm.name())
        .description(createRoleForm.description())
        .isActive(createRoleForm.active())
        .rolePermissions(createRoleForm.permissions()
            .stream()
            .map(permission -> RolePermissions.builder()
                .permission(permission)
                .build())
            .collect(Collectors.toSet()))
        .expiresAt(createRoleForm.expiresAt() != null ? new Date(createRoleForm.expiresAt() * 1000L)
            : null)
        .build();

    return conversionService.convert(roleService.createRole(role), RoleResponse.class);
  }

  public RoleResponse update(final String key, final UpdateRole updateRoleForm, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionRoleTypes.ROLE_UPDATE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized"));

    final Role role = roleRepository.findFirstByName(key)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "role_not_found"));

    if (updateRoleForm.name() != null) {
      role.setName(updateRoleForm.name());
    }
    if (updateRoleForm.description() != null) {
      role.setDescription(updateRoleForm.description());
    }
    if (updateRoleForm.active() != null) {
      role.setActive(updateRoleForm.active());
    }
    if (updateRoleForm.permissions() != null) {
      role.setRolePermissions(updateRoleForm.permissions()
          .stream()
          .map(permission -> RolePermissions.builder()
              .permission(permission)
              .build())
          .collect(Collectors.toSet()));
    }
    if (updateRoleForm.expiresAt() != null) {
      role.setExpiresAt(new Date(updateRoleForm.expiresAt() * 1000L));
    }

    return conversionService.convert(roleService.updateRole(role), RoleResponse.class);
  }

  public void delete(final String key, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionRoleTypes.ROLE_DELETE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized"));

    final Role role = roleRepository.findFirstByName(key)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "role_not_found"));

    roleService.deleteRole(role);
  }
}