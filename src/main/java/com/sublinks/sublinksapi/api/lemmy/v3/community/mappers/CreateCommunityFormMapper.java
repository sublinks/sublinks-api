package com.sublinks.sublinksapi.api.lemmy.v3.community.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CreateCommunity;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class})
public abstract class CreateCommunityFormMapper {
    @Mapping(target = "linkPersonCommunity", ignore = true)
    @Mapping(target = "communityAggregate", ignore = true)
    @Mapping(target = "instance", source = "instance")
    @Mapping(target = "titleSlug", source = "createCommunityForm.name")
    @Mapping(target = "title", source = "createCommunityForm.title")
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
    public abstract Community map(CreateCommunity createCommunityForm, Instance instance, KeyStore keys);

    String mapTitleSlug(CreateCommunity createCommunityForm) {
        return createCommunityForm.name()
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replace("/ +/g", "_");
    }
}
