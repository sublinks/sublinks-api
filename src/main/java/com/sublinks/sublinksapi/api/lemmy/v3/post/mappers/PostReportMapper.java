package com.sublinks.sublinksapi.api.lemmy.v3.post.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostReportMapper extends
    Converter<com.sublinks.sublinksapi.post.dto.PostReport, PostReport> {

  @Override
  @Mapping(target = "published", source = "post.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updated", source = "post.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "creator_id", source = "postReport.creator.id")
  @Mapping(target = "resolver_id", source = "postReport.resolver.id")
  @Mapping(target = "post_id", source = "postReport.post.id")
  @Mapping(target = "original_post_name", source = "postReport.originalTitle")
  @Mapping(target = "original_post_url", source = "postReport.originalUrl")
  @Mapping(target = "original_post_body", source = "postReport.originalBody")
  PostReport convert(@Nullable com.sublinks.sublinksapi.post.dto.PostReport postReport);
}
