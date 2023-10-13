package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.announcment.Announcement;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Language;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetSiteResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.SiteResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.instance.LocalInstanceContext;
import com.fedilinks.fedilinksapi.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocalInstanceContextMapper.class, TaglineMapper.class, PersonMapper.class})
public interface SiteMapper {

    @Mapping(target = "tag_line", ignore = true)
    @Mapping(target = "site_view", source = "context")
    @Mapping(target = "tag_lines", source = "announcements")
    SiteResponse toSiteResponse(LocalInstanceContext context, Collection<Announcement> announcements);


    @Mapping(target = "version", constant = "0.1.0")
    @Mapping(target = "taglines", source = "announcements")
    @Mapping(target = "tagline", ignore = true)
    @Mapping(target = "site_view", source = "context")
    @Mapping(target = "my_user", source = "loggedInPerson")
    @Mapping(target = "discussion_languages", source = "discussionLanguages")
    @Mapping(target = "discussion_language", ignore = true)
    @Mapping(target = "custom_emojis", source = "customEmojis")
    @Mapping(target = "custom_emoji", ignore = true)
    @Mapping(target = "all_languages", source = "languages")
    @Mapping(target = "all_language", ignore = true)
    @Mapping(target = "admins", source = "admins")
    @Mapping(target = "admin", ignore = true)
    GetSiteResponse toGetSiteResponse(
            LocalInstanceContext context,
            Person loggedInPerson,
            Collection<Announcement> announcements,
            Collection<PersonView> admins,
            Collection<Language> languages,
            Collection<CustomEmojiView> customEmojis,
            Collection<Integer> discussionLanguages
    );
}
