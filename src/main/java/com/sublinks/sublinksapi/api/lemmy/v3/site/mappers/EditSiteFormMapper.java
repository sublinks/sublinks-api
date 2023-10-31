package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.EditSite;
import com.sublinks.sublinksapi.instance.dto.Instance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EditSiteFormMapper {
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "people", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "communities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publicKey", ignore = true)
    @Mapping(target = "privateKey", ignore = true)
    @Mapping(target = "activityPubId", ignore = true)
    @Mapping(target = "domain", ignore = true)
    @Mapping(target = "software", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "description", expression = "java(entity.description() == null ? null : entity.description())")
    @Mapping(target = "sidebar", expression = "java(entity.sidebar() == null ? null : entity.sidebar())")
    @Mapping(target = "bannerUrl", constant = "")
    @Mapping(target = "iconUrl", constant = "")
    void map(EditSite entity,  @MappingTarget Instance instance);
}
