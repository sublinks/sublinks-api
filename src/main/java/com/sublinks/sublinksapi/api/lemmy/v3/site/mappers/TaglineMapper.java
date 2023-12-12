package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcement.dto.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Tagline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaglineMapper extends Converter<Announcement, Tagline> {

  @Override
  @Mapping(target = "published", source = "announcement.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "updated", source = "announcement.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  Tagline convert(@Nullable Announcement announcement);
}
