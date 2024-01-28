package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcement.dto.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Tagline;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaglineMapper extends Converter<Announcement, Tagline> {

  @Override
  @Mapping(target = "published", source = "announcement.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updated", source = "announcement.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  Tagline convert(@Nullable Announcement announcement);
}
