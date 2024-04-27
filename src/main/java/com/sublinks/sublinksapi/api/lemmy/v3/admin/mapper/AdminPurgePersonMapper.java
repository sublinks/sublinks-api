package com.sublinks.sublinksapi.api.lemmy.v3.admin.mapper;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgePerson;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * The AdminPurgePersonMapper interface is responsible for mapping a ModerationLog object to an
 * AdminPurgePerson object.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminPurgePersonMapper extends Converter<ModerationLog, AdminPurgePerson> {

  @Override
  @Mapping(target = "id", source = "moderationLog.id")
  @Mapping(target = "when_", source = "moderationLog.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "admin_person_id", source = "moderationLog.adminPersonId")
  @Mapping(target = "reason", source = "moderationLog.reason")
  AdminPurgePerson convert(@Nullable ModerationLog moderationLog);
}
