package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.request;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.CreateSite;
import com.sublinksapp.sublinksappapi.instance.Instance;
import com.sublinksapp.sublinksappapi.instance.LocalInstanceContext;
import com.sublinksapp.sublinksappapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateSiteFormMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "domain", source = "context.settings.baseUrl")
    @Mapping(target = "activityPubId", source = "context.settings.baseUrl")
    @Mapping(target = "software", constant = "sublinksapp")
    @Mapping(target = "version", constant = "0.1.0")
    @Mapping(target = "description", expression = "java(entity.description() == null ? null : entity.description())")
    @Mapping(target = "sidebar", expression = "java(entity.sidebar() == null ? null : entity.sidebar())")
    @Mapping(target = "bannerUrl", constant = "")
    @Mapping(target = "iconUrl", constant = "")
    @Mapping(target = "publicKey", source = "keys.publicKey")
    @Mapping(target = "privateKey", source = "keys.privateKey")
    void CreateSiteToInstance(CreateSite entity, LocalInstanceContext context, KeyStore keys, @MappingTarget Instance instance);
}
