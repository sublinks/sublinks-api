package com.sublinks.sublinksapi.api.lemmy.v3.modlog.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModRemoveCommunity;
import com.sublinks.sublinksapi.moderation.dto.ModerationLog;
import com.sublinks.sublinksapi.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModRemoveCommunityMapper extends Converter<ModerationLog, ModRemoveCommunity> {

  @Override
  @Mapping(target = "id", source = "moderationLog.id")
  @Mapping(target = "when_", source = "moderationLog.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "mod_person_id", source = "moderationLog.moderationPersonId")
  @Mapping(target = "community_id", source = "moderationLog.communityId")
  @Mapping(target = "reason", source = "moderationLog.reason")
  @Mapping(target = "removed", source = "moderationLog.removed")
  @Mapping(target = "expires", source = "moderationLog.expires",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  ModRemoveCommunity convert(@Nullable ModerationLog moderationLog);
}
