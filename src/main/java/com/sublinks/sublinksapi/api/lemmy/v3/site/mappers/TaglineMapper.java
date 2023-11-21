package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcment.dto.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Tagline;
import java.util.Collection;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaglineMapper extends Converter<Collection<Announcement>, Set<Tagline>> {

  @Override
  @Mapping(target = "local_site_id", constant = "")
  @Mapping(target = "content", constant = "")
  @Mapping(target = "published", constant = "")
  @Mapping(target = "updated", constant = "")
  Set<Tagline> convert(@Nullable Collection<Announcement> announcement);
}
