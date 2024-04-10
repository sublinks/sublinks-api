package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.RegistrationApplication;
import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonRegistrationApplicationMapper extends
    Converter<PersonRegistrationApplication, RegistrationApplication> {

  @Override
  @Mapping(target = "id", source = "personRegistrationApplication.id")
  @Mapping(target = "published", source = "personRegistrationApplication.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "local_user_id", source = "personRegistrationApplication.person.id")
  @Mapping(target = "answer", source = "personRegistrationApplication.answer")
  @Mapping(target = "deny_reason", constant = "")
  @Mapping(target = "admin_id", source = "personRegistrationApplication.admin.id")

  RegistrationApplication convert(
      @Nullable PersonRegistrationApplication personRegistrationApplication);
}
