package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.CreateSite;
import com.sublinks.sublinksapi.instance.Instance;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateSiteFormMapper {
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "people", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "communities", ignore = true)
    @Mapping(target = "instanceAggregate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publicKey", ignore = true)
    @Mapping(target = "privateKey", ignore = true)
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "domain", source = "context.settings.baseUrl")
    @Mapping(target = "activityPubId", source = "context.settings.baseUrl")
    @Mapping(target = "software", constant = "sublinks")
    @Mapping(target = "version", constant = "0.1.0")
    @Mapping(target = "description", expression = "java(entity.description() == null ? null : entity.description())")
    @Mapping(target = "sidebar", expression = "java(entity.sidebar() == null ? null : entity.sidebar())")
    @Mapping(target = "bannerUrl", constant = "")
    @Mapping(target = "iconUrl", constant = "")
    void map(CreateSite entity, LocalInstanceContext context, @MappingTarget Instance instance);
}
