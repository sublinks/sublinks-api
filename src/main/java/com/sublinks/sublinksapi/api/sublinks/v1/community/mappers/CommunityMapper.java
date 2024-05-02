package com.sublinks.sublinksapi.api.sublinks.v1.community.mappers;

import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.languages.mappers.LanguageMapper;
import com.sublinks.sublinksapi.api.sublinks.v1.utils.DateUtils;
import com.sublinks.sublinksapi.community.entities.Community;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LanguageMapper.class})
public abstract class CommunityMapper implements Converter<Community, CommunityResponse> {

  @Override
  @Mapping(target = "key", source = "community.titleSlug")
  @Mapping(target = "title", source = "community.title")
  @Mapping(target = "titleSlug", source = "community.titleSlug")
  @Mapping(target = "description", source = "community.description")
  @Mapping(target = "iconImageUrl", source = "community.iconImageUrl")
  @Mapping(target = "bannerImageUrl", source = "community.bannerImageUrl")
  @Mapping(target = "activityId", source = "community.activityPubId")
  @Mapping(target = "languages", source = "community.languages", expression = "java(community.getLanguages().stream().map(languageMapper::convert).toList())")
  @Mapping(target = "isLocal", source = "community.isLocal")
  @Mapping(target = "isDeleted", source = "community.isDeleted")
  @Mapping(target = "isRemoved", source = "community.isRemoved")
  @Mapping(target = "isNsfw", source = "community.isNsfw")
  @Mapping(target = "isPostingRestrictedToMods", source = "community.isPostingRestrictedToMods")
  @Mapping(target = "publicKey", source = "community.publicKey")
  @Mapping(target = "createdAt", source = "community.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updatedAt", source = "community.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  public abstract CommunityResponse convert(@Nullable Community community);
}
