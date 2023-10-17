package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.response;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityResponseMapper {
    //@Mapping(target = "community_view", source = "community")
    //@Mapping(target = "discussion_languages", source = "community")
    //CommunityResponse map(Community community);
}
