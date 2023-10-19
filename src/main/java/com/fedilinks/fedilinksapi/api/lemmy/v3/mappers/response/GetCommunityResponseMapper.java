package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.response;

import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.SiteMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetCommunityResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityView;
import com.fedilinks.fedilinksapi.instance.LocalInstanceContext;
import com.fedilinks.fedilinksapi.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class, SiteMapper.class})
public interface GetCommunityResponseMapper {
    @Mapping(target = "site", source = "site")
    @Mapping(target = "moderators", source = "moderators")
    @Mapping(target = "discussion_languages", source = "languages")
    @Mapping(target = "community_view", source = "communityView")
    GetCommunityResponse map(CommunityView communityView,
                             List<String> languages,
                             Set<Person> moderators,
                             LocalInstanceContext site
    );
}
