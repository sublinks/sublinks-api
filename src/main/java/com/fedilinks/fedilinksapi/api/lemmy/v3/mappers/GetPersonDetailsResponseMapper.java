package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetPersonDetailsResponse;
import com.fedilinks.fedilinksapi.person.PersonContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyPersonMapper.class})
public interface GetPersonDetailsResponseMapper {

    @Mapping(target = "person_view", source = "personContext")
    @Mapping(target = "posts", source = "personContext.posts")
    @Mapping(target = "moderates", source = "personContext.moderates")
    @Mapping(target = "comments", source = "personContext.comments")
    GetPersonDetailsResponse PersonToGetPersonDetailsResponse(PersonContext personContext);
}
