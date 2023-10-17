package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.request;

import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.CreateCommunity;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.instance.Instance;
import com.fedilinks.fedilinksapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class})
public abstract class CreateCommunityFormMapper {
    @Mapping(target = "instance", source = "instance")
    @Mapping(target = "titleSlug", source = "createCommunityForm.name")
    @Mapping(target = "description", source = "createCommunityForm.description")
    @Mapping(target = "isPostingRestrictedToMods", source = "createCommunityForm.posting_restricted_to_mods")
    @Mapping(target = "isNsfw", source = "createCommunityForm.nsfw")
    @Mapping(target = "publicKey", source = "keys.publicKey")
    @Mapping(target = "privateKey", source = "keys.privateKey")
    @Mapping(target = "isRemoved", constant = "false")
    @Mapping(target = "isLocal", constant = "true")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "iconImageUrl", constant = "")
    @Mapping(target = "bannerImageUrl", constant = "")
    @Mapping(target = "activityPubId", constant = "")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Community createCommunityFormToCommunity(CreateCommunity createCommunityForm, Instance instance, KeyStore keys);

    String mapTitleSlug(CreateCommunity createCommunityForm) {
        return createCommunityForm.name()
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replace("/ +/g", "_");
    }
}
