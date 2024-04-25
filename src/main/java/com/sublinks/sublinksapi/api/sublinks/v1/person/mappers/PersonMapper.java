package com.sublinks.sublinksapi.api.sublinks.v1.person.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.mappers.RoleMapper;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public abstract class PersonMapper implements Converter<Person, PersonResponse> {

  @Override
  @Mapping(target = "key", source = "person.name")
  @Mapping(target = "name", source = "person.name")
  @Mapping(target = "displayName", source = "person", qualifiedByName = "display_name")
  @Mapping(target = "avatar", source = "person", qualifiedByName = "avatar")
  @Mapping(target = "banner", source = "person", qualifiedByName = "banner")
  @Mapping(target = "isBanned", source = "person", qualifiedByName = "is_banned")
  @Mapping(target = "banExpiresAt", source = "person.role.expiresAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "isDeleted", source = "person.deleted")
  @Mapping(target = "isBotAccount", source = "person.botAccount")
  @Mapping(target = "role", expression = "java(roleMapper.convert(person.getRole()))")
  @Mapping(target = "languages", source = "person.languages")
  @Mapping(target = "createdAt", source = "person.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updatedAt", source = "person.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  public abstract PersonResponse convert(@Nullable Person person);

  @Named("is_banned")
  boolean mapIsBanned(Person person) {

    return RoleAuthorizingService.isBanned(person);
  }

  @Named("display_name")
  String mapDisplayName(Person person) {

    return !person.getDisplayName().isBlank() ? person.getDisplayName() : null;
  }

  @Named("avatar")
  String mapAvatar(Person person) {

    return !person.getAvatarImageUrl().isBlank() ? person.getAvatarImageUrl() : null;
  }

  @Named("banner")
  String mapBanner(Person person) {

    return !person.getBannerImageUrl().isBlank() ? person.getBannerImageUrl() : null;
  }
}
