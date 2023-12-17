package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcement.dto.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Tagline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.utils.DateUtils;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaglineMapper extends Converter<Announcement, Tagline> {

  @Override
  @Mapping(target = "published", source = "announcement.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updated", source = "announcement.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  Tagline convert(@Nullable Announcement announcement);
}
