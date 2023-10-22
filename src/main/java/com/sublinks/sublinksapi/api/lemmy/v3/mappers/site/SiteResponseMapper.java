package com.sublinks.sublinksapi.api.lemmy.v3.mappers.site;

import com.sublinks.sublinksapi.announcment.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.LocalInstanceContextMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.TaglineMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.SiteResponse;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocalInstanceContextMapper.class, TaglineMapper.class, LemmyPersonMapper.class})
public interface SiteResponseMapper {

    @Mapping(target = "tag_line", ignore = true)
    @Mapping(target = "site_view", source = "context")
    @Mapping(target = "tag_lines", source = "announcements")
    public SiteResponse map(LocalInstanceContext context, Collection<Announcement> announcements);
}
