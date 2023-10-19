package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.response.CommunityResponseMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.person.Person;
import com.fedilinks.fedilinksapi.person.PersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PersonMapper.class, CommunityResponseMapper.class})
public interface CommunityModeratorViewMapper {
    @Mapping(target = "moderator", source = "person")
    CommunityModeratorView map(Community community, Person person);
}
