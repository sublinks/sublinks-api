package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.community;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinksapp.sublinksappapi.community.Community;
import com.sublinksapp.sublinksappapi.person.Person;
import com.sublinksapp.sublinksappapi.person.PersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PersonMapper.class, CommunityResponseMapper.class})
public interface CommunityModeratorViewMapper {
    @Mapping(target = "moderator", source = "person")
    CommunityModeratorView map(Community community, Person person);
}
