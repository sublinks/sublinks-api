package com.sublinks.sublinksapi.api.lemmy.v3.modlog.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModBan;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModBanMapper extends Converter<ModerationLog, ModBan> {

  @Override
  @Mapping(target = "id", source = "moderationLog.id")
  @Mapping(target = "when_", source = "moderationLog.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "mod_person_id", source = "moderationLog.moderationPersonId")
  @Mapping(target = "other_person_id", source = "moderationLog.otherPersonId")
  @Mapping(target = "reason", source = "moderationLog.reason")
  @Mapping(target = "banned", source = "moderationLog.banned")
  @Mapping(target = "expires", source = "moderationLog.expires",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  ModBan convert(@Nullable ModerationLog moderationLog);
}
