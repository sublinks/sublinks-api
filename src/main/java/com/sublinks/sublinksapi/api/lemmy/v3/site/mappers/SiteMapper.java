package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Site;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SiteMapper extends Converter<LocalInstanceContext, Site> {
    @Override
    @Mapping(target = "id", source = "context.instance.id")
    @Mapping(target = "name", source = "context.instance.name")
    @Mapping(target = "sidebar", source = "context.instance.sidebar")
    @Mapping(target = "published", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "updated", source = "context.instance.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "icon", source = "context.instance.iconUrl")
    @Mapping(target = "banner", source = "context.instance.bannerUrl")
    @Mapping(target = "description", source = "context.instance.description")
    @Mapping(target = "actor_id", source = "context.instance.domain")
    @Mapping(target = "last_refreshed_at", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "inbox_url", source = "context.instance.domain")
    @Mapping(target = "public_key", source = "context.instance.publicKey")
    @Mapping(target = "instance_id", source = "context.instance.id")
    Site convert(@Nullable LocalInstanceContext context);
}
