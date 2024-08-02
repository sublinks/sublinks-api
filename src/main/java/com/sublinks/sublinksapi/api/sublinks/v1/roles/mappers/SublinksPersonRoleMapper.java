package com.sublinks.sublinksapi.api.sublinks.v1.roles.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.RoleResponse;
import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.entities.RolePermissions;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import java.util.Date;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {RolePermissionService.class})
public abstract class SublinksPersonRoleMapper implements Converter<Role, RoleResponse> {

  @Override
  @Mapping(target = "key", source = "role.name")
  @Mapping(target = "name", source = "role.name")
  @Mapping(target = "description", source = "role.description")
  @Mapping(target = "permissions", source = "role", qualifiedByName = "permissions")
  @Mapping(target = "isActive", source = "role.active")
  @Mapping(target = "isExpired", source = "role", qualifiedByName = "is_expired")
  @Mapping(target = "expiresAt",
      source = "role.expiresAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "createdAt",
      source = "role.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updatedAt",
      source = "role.updatedAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  public abstract RoleResponse convert(@Nullable Role role);

  @Named("is_expired")
  Boolean mapIsExpired(Role role) {

    if (role.getExpiresAt() == null) {
      return false;
    }
    return new Date().after(role.getExpiresAt());
  }

  @Named("permissions")
  List<String> mapPermissions(Role role) {

    return role.getRolePermissions()
        .stream()
        .map(RolePermissions::getPermission)
        .toList();
  }
}
