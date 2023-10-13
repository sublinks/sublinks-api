package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.CreateSite;
import com.fedilinks.fedilinksapi.instance.Instance;
import com.fedilinks.fedilinksapi.instance.LocalInstanceContext;
import com.fedilinks.fedilinksapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateSiteRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "domain", source = "context.settings.baseUrl")
    @Mapping(target = "activityPubId", source = "context.settings.baseUrl")
    @Mapping(target = "software", constant = "fedilinks")
    @Mapping(target = "version", constant = "0.1.0")
    @Mapping(target = "description", expression = "java(entity.description() == null ? null : entity.description())")
    @Mapping(target = "sidebar", expression = "java(entity.sidebar() == null ? null : entity.sidebar())")
    @Mapping(target = "bannerUrl", constant = "")
    @Mapping(target = "iconUrl", constant = "")
    @Mapping(target = "publicKey", source = "keys.publicKey")
    @Mapping(target = "privateKey", source = "keys.privateKey")
    void CreateSiteToInstance(CreateSite entity, LocalInstanceContext context, KeyStore keys, @MappingTarget Instance instance);
}
