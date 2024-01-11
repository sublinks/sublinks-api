package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.mappers;

import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.customemoji.dto.CustomEmojiKeyword;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomEmojiViewMapper
    extends Converter<com.sublinks.sublinksapi.customemoji.dto.CustomEmoji, CustomEmojiView> {

  @Mapping(target = "custom_emoji.id", source = "customEmoji.id")
  @Mapping(target = "custom_emoji.local_site_id", source = "customEmoji.localSiteId")
  @Mapping(target = "custom_emoji.shortcode", source = "customEmoji.shortCode")
  @Mapping(target = "custom_emoji.image_url", source = "customEmoji.imageUrl")
  @Mapping(target = "custom_emoji.alt_text", source = "customEmoji.altText")
  @Mapping(target = "custom_emoji.category", source = "customEmoji.category")
  @Mapping(target = "custom_emoji.published", source = "customEmoji.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "custom_emoji.updated", source = "customEmoji.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "keywords", source = "customEmoji.keywords", qualifiedByName = "mapKeywords")
  CustomEmojiView convert(@Nullable com.sublinks.sublinksapi.customemoji.dto.CustomEmoji customEmoji);

  @Named("mapKeywords")
  default List<String> map(List<CustomEmojiKeyword> keywords) {
    var retVal = new ArrayList<String>();
    keywords.forEach(k -> retVal.add(k.getKeyword()));
    return retVal;
  }

}
