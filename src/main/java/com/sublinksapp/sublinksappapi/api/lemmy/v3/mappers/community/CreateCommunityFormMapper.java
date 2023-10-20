package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.community;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.CreateCommunity;
import com.sublinksapp.sublinksappapi.community.Community;
import com.sublinksapp.sublinksappapi.instance.Instance;
import com.sublinksapp.sublinksappapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyCommunityMapper.class})
public abstract class CreateCommunityFormMapper {
    @Mapping(target = "linkPersonCommunity", ignore = true)
    @Mapping(target = "communityAggregates", ignore = true)
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
    public abstract Community createCommunityFormToCommunity(CreateCommunity createCommunityForm, Instance instance, KeyStore keys);

    String mapTitleSlug(CreateCommunity createCommunityForm) {
        return createCommunityForm.name()
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replace("/ +/g", "_");
    }
}
