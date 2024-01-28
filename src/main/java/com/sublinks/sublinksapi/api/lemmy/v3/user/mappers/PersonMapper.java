package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper extends
    Converter<Person, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person> {

  @Override
  @Mapping(target = "id", source = "person.id")
  @Mapping(target = "name", source = "person.name")
  @Mapping(target = "display_name", source = "person")
  @Mapping(target = "avatar", source = "person.avatarImageUrl")
  @Mapping(target = "banner", source = "person.bannerImageUrl")
  @Mapping(target = "banned", source = "person")
  @Mapping(target = "ban_expires", source = "person.role.expiresAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "published", source = "person.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updated", source = "person.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "bio", source = "person.biography")
  @Mapping(target = "local", source = "person.local")
  @Mapping(target = "deleted", source = "person.deleted")
  @Mapping(target = "inbox_url", constant = "") // @todo inbox_url
  @Mapping(target = "shared_inbox_url", constant = "") // @todo shared_inbox_url
  @Mapping(target = "matrix_user_id", source = "person.matrixUserId")
  @Mapping(target = "bot_account", source = "person.botAccount")
  @Mapping(target = "instance_id", source = "person.instance.id")
  com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person convert(@Nullable Person person);

  default boolean mapIsBanned(Person person) {

    return RoleAuthorizingService.isBanned(person);
  }

  default String mapDisplayName(Person person) {

    if (person.getDisplayName().isBlank()) {
      return null;
    }
    return person.getDisplayName();
  }
}
