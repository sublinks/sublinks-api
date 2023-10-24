package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcment.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Tagline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaglineMapper {

    @Mapping(target = "local_site_id", constant = "")
    @Mapping(target = "content", constant = "")
    @Mapping(target = "published", constant = "")
    @Mapping(target = "updated", constant = "")
    Set<Tagline> announcementToTagline(Collection<Announcement> announcement);
}
