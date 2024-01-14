package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.dto.Person;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@AllArgsConstructor
public class PersonMapperImpl implements PersonMapper {

  private final RoleAuthorizingService roleAuthorizingService;


  @Override
  public com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person convert(@Nullable Person person) {

    if (person == null) {
      return null;
    }

    final boolean isBanned = roleAuthorizingService.isBanned(person);

    com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.PersonBuilder mapped_person = com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.builder();

    mapped_person.id(person.getId());
    mapped_person.name(person.getName());
    mapped_person.display_name(person.getDisplayName());
    mapped_person.avatar(person.getAvatarImageUrl());
    mapped_person.banned(isBanned);
    if (isBanned && person.getRole().getExpiresAt() != null) {
      mapped_person.ban_expires(
          LocalDateTime.parse(person.getRole().getExpiresAt().toString()).format(
              DateUtils.FRONT_END_DATETIME_FORMATTER));
    }

    mapped_person.published(LocalDateTime.parse(person.getCreatedAt().toString()).format(
        DateUtils.FRONT_END_DATETIME_FORMATTER));
    mapped_person.updated(LocalDateTime.parse(person.getUpdatedAt().toString()).format(
        DateUtils.FRONT_END_DATETIME_FORMATTER));
    mapped_person.actor_id(person.getActivityPubId());
    mapped_person.bio(person.getBiography());
    mapped_person.local(person.isLocal());
    mapped_person.banner(person.getBannerImageUrl());
    mapped_person.deleted(person.isDeleted());
    mapped_person.inbox_url("");
    mapped_person.shared_inbox_url("");
    mapped_person.matrix_user_id("");
    mapped_person.bot_account(person.isBotAccount());
    mapped_person.instance_id(person.getInstance().getId());



    return mapped_person.build();
  }
}
