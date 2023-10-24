package com.sublinks.sublinksapi.api.lemmy.v3.community.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.GetCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.SiteResponseMapper;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class, SiteResponseMapper.class})
public interface GetCommunityResponseMapper {
    @Mapping(target = "site", source = "site")
    @Mapping(target = "moderators", source = "moderators")
    @Mapping(target = "discussion_languages", source = "languages")
    @Mapping(target = "community_view", source = "communityView")
    GetCommunityResponse map(CommunityView communityView,
                             Set<String> languages,
                             List<CommunityModeratorView> moderators,
                             LocalInstanceContext site
    );
}
