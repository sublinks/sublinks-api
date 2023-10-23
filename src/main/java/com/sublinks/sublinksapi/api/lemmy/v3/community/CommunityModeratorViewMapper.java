package com.sublinks.sublinksapi.api.lemmy.v3.community;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PersonMapper.class, CommunityResponseMapper.class})
public interface CommunityModeratorViewMapper {
    @Mapping(target = "moderator", source = "person")
    CommunityModeratorView map(Community community, Person person);
}
