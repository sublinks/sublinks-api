package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper extends
    Converter<Person, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person> {

  @Override
  @Mapping(target = "id", source = "person.id")
  @Mapping(target = "name", source = "person.name")
  @Mapping(target = "display_name", source = "person.displayName",
      conditionExpression = "java(!person.getDisplayName().isEmpty())")
  @Mapping(target = "avatar", source = "person.avatarImageUrl")
  @Mapping(target = "banned", source = "person.banned")
  @Mapping(target = "published", source = "person.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updated", source = "person.updatedAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "actor_id", source = "person.activityPubId")
  @Mapping(target = "bio", source = "person.biography")
  @Mapping(target = "local", source = "person.local")
  @Mapping(target = "banner", source = "person.bannerImageUrl")
  @Mapping(target = "deleted", source = "person.deleted")
  @Mapping(target = "inbox_url", constant = "")
  @Mapping(target = "shared_inbox_url", constant = "")
  @Mapping(target = "matrix_user_id", constant = "")
  @Mapping(target = "admin", constant = "true")
  @Mapping(target = "bot_account", source = "person.botAccount")
  @Mapping(target = "ban_expires", constant = "")
  @Mapping(target = "instance_id", source = "person.instance.id")
  com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person convert(@Nullable Person person);
}
