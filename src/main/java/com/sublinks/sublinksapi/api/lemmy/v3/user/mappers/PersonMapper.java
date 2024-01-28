package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PersonMapper implements
    Converter<Person, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person> {

  @Override
  @Mapping(target = "id", source = "person.id")
  @Mapping(target = "name", source = "person.name")
  @Mapping(target = "avatar", source = "person", qualifiedByName="avatar")
  @Mapping(target = "banner", source = "person", qualifiedByName="banner")
  @Mapping(target = "banned", source = "person", qualifiedByName="is_banned")
  @Mapping(target = "display_name", source = "person", qualifiedByName="display_name")
  @Mapping(target = "ban_expires", source = "person.role.expiresAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "published", source = "person.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updated", source = "person.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "actor_id", source = "person.actorId")
  @Mapping(target = "bio", source = "person.biography")
  @Mapping(target = "local", source = "person.local")
  @Mapping(target = "deleted", source = "person.deleted")
  @Mapping(target = "inbox_url", constant = "") // @todo inbox_url
  @Mapping(target = "shared_inbox_url", constant = "") // @todo shared_inbox_url
  @Mapping(target = "matrix_user_id", source = "person.matrixUserId")
  @Mapping(target = "bot_account", source = "person.botAccount")
  @Mapping(target = "instance_id", source = "person.instance.id")
  public abstract com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person convert(
      @Nullable Person person);

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
