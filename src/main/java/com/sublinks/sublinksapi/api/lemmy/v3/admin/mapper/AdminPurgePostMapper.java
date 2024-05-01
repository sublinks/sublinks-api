package com.sublinks.sublinksapi.api.lemmy.v3.admin.mapper;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgePost;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * This interface serves as a Mapper for converting ModerationLog objects to AdminPurgePost
 * objects.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminPurgePostMapper extends Converter<ModerationLog, AdminPurgePost> {

  @Override
  @Mapping(target = "id", source = "moderationLog.id")
  @Mapping(target = "when_", source = "moderationLog.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "admin_person_id", source = "moderationLog.adminPersonId")
  @Mapping(target = "community_id", source = "moderationLog.communityId")
  @Mapping(target = "reason", source = "moderationLog.reason")
  AdminPurgePost convert(@Nullable ModerationLog moderationLog);
}
