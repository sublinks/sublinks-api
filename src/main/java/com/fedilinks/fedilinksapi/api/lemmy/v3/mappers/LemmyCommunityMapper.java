package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LemmyCommunityMapper {

    @Mapping(target = "posting_restricted_to_mods", source = "community.postingRestrictedToMods")
    @Mapping(target = "name", source = "community.title")
    @Mapping(target = "instance_id", source = "community.instance.id")
    @Mapping(target = "inbox_url", constant = "")
    @Mapping(target = "icon", source = "community.iconImageUrl")
    @Mapping(target = "hidden", constant = "false")
    @Mapping(target = "followers_url", constant = "")
    @Mapping(target = "banner", source = "community.bannerImageUrl")
    @Mapping(target = "actor_id", constant = "")
    @Mapping(target = "updated", source = "community.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "published", source = "community.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    Community communityToLemmyCommunity(com.fedilinks.fedilinksapi.community.Community community);
}
