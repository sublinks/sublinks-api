package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcment.dto.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocalInstanceContextMapper.class, TaglineMapper.class, LemmyPersonMapper.class})
public interface SiteResponseMapper {

    @Mapping(target = "site_view", source = "context")
    @Mapping(target = "tag_lines", source = "announcements")
    SiteResponse map(LocalInstanceContext context, Collection<Announcement> announcements);
}
