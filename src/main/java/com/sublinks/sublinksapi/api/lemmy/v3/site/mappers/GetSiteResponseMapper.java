package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.announcment.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Language;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocalInstanceContextMapper.class, TaglineMapper.class, LemmyPersonMapper.class})
public interface GetSiteResponseMapper {
    @Mapping(target = "version", constant = "0.1.0")
    @Mapping(target = "taglines", source = "announcements")
    @Mapping(target = "site_view", source = "context")
    @Mapping(target = "my_user", source = "personContext", conditionExpression = "java(personContext.getPerson() != null)")
    @Mapping(target = "discussion_languages", source = "discussionLanguages")
    @Mapping(target = "custom_emojis", source = "customEmojis")
    @Mapping(target = "all_languages", source = "languages")
    @Mapping(target = "admins", source = "admins")
    public GetSiteResponse map(
            LocalInstanceContext context,
            PersonContext personContext,
            Collection<Announcement> announcements,
            Collection<Person> admins,// todo collection of PersonContext
            Collection<Language> languages,
            Collection<CustomEmojiView> customEmojis,
            Collection<Long> discussionLanguages
    );
}
