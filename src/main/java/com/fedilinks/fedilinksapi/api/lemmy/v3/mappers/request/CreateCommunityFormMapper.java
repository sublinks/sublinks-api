package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.request;

import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.CreateCommunity;
import com.fedilinks.fedilinksapi.community.Community;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class})
public abstract class CreateCommunityFormMapper {
    @Mapping(target = "titleSlug", source = "createCommunityForm")
    @Mapping(target = "isPostingRestrictedToMods", source = "createCommunityForm.posting_restricted_to_mods")
    @Mapping(target = "isNsfw", source = "createCommunityForm.nsfw")
    @Mapping(target = "nsfwType", constant = "nsfw")
    @Mapping(target = "isRemoved", constant = "false")
    @Mapping(target = "isLocal", constant = "true")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "bannerImageUrl", ignore = true)
    @Mapping(target = "activityPubId", ignore = true)
    @Mapping(target = "instanceId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "iconImageUrl", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Community createCommunityFormToCommunity(CreateCommunity createCommunityForm);

    String mapTitleSlug(CreateCommunity createCommunityForm) {
        return createCommunityForm.name()
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replace("/ +/g", "-");
    }
}
