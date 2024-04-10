package com.sublinks.sublinksapi.api.lemmy.v3.modlog.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModLockPost;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModLockPostMapper extends Converter<ModerationLog, ModLockPost> {

  @Override
  @Mapping(target = "id", source = "moderationLog.id")
  @Mapping(target = "when_", source = "moderationLog.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "mod_person_id", source = "moderationLog.moderationPersonId")
  @Mapping(target = "post_id", source = "moderationLog.postId")
  @Mapping(target = "locked", source = "moderationLog.locked")
  ModLockPost convert(@Nullable ModerationLog moderationLog);
}
