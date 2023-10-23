package com.sublinks.sublinksapi.api.lemmy.v3.community;

import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class})
public interface CommunityResponseMapper {
    @Mapping(target = "community_view", source = "communityView")
    @Mapping(target = "discussion_languages", source = "languages")
    CommunityResponse map(
            CommunityView communityView,
            List<String> languages
    );
}
