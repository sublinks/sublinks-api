package com.sublinks.sublinksapi.api.lemmy.v3.community.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityMapper extends Converter<com.sublinks.sublinksapi.community.dto.Community, Community> {
    @Override
    @Mapping(target = "posting_restricted_to_mods", source = "community.postingRestrictedToMods")
    @Mapping(target = "name", source = "community.titleSlug")
    @Mapping(target = "instance_id", source = "community.instance.id")
    @Mapping(target = "hidden", constant = "false")
    @Mapping(target = "updated", source = "community.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "published", source = "community.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "actor_id", source = "activityPubId")
    @Mapping(target = "banner", source = "community.bannerImageUrl")
    @Mapping(target = "icon", source = "community.iconImageUrl")
    @Mapping(target = "inbox_url", constant = "https://aol.com")
    @Mapping(target = "followers_url", constant = "https://yahoo.com")
    Community convert(@Nullable com.sublinks.sublinksapi.community.dto.Community community);
}
