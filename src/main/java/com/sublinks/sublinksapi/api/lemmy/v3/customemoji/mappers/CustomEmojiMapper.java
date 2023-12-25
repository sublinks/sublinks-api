package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmoji;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomEmojiMapper extends
    Converter<com.sublinks.sublinksapi.customemoji.dto.CustomEmoji, CustomEmoji> {

  @Override
  @Mapping(target = "updated", source = "customEmoji.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "published", source = "customEmoji.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "id", source = "customEmoji.id")
  @Mapping(target = "local_site_id", source = "customEmoji.localSiteId")
  @Mapping(target = "shortcode", source = "customEmoji.shortCode")
  @Mapping(target = "image_url", source = "customEmoji.imageUrl")
  @Mapping(target = "alt_text", source = "customEmoji.altText")
  @Mapping(target = "category", source = "customEmoji.category")
  CustomEmoji convert(@Nullable com.sublinks.sublinksapi.customemoji.dto.CustomEmoji customEmoji);
}
