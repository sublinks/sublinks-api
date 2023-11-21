package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.LocalSiteRateLimit;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocalSiteRateLimitMapper extends
    Converter<LocalInstanceContext, LocalSiteRateLimit> {

  @Override
  @Mapping(target = "id", source = "context.instance.id")
  @Mapping(target = "local_site_id", source = "context.instance.id")
  @Mapping(target = "updated", source = "context.instance.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "published", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "search_per_second", source = "context.rateLimits.searchPerSecond")
  @Mapping(target = "search", source = "context.rateLimits.search")
  @Mapping(target = "register_per_second", source = "context.rateLimits.registerPerSecond")
  @Mapping(target = "register", source = "context.rateLimits.register")
  @Mapping(target = "post_per_second", source = "context.rateLimits.postPerSecond")
  @Mapping(target = "post", source = "context.rateLimits.post")
  @Mapping(target = "message_per_second", source = "context.rateLimits.messagePerSecond")
  @Mapping(target = "message", source = "context.rateLimits.message")
  @Mapping(target = "image_per_second", source = "context.rateLimits.imagePerSecond")
  @Mapping(target = "image", source = "context.rateLimits.image")
  @Mapping(target = "comment_per_second", source = "context.rateLimits.commentPerSecond")
  @Mapping(target = "comment", source = "context.rateLimits.comment")
  LocalSiteRateLimit convert(@Nullable LocalInstanceContext context);
}
