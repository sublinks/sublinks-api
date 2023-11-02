package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocalUserMapper extends Converter<Person, LocalUser> {
    @Override
    @Mapping(target = "totp_2fa_enabled", constant = "true")
    @Mapping(target = "post_listing_mode", constant = "List")
    @Mapping(target = "infinite_scroll_enabled", constant = "true")
    @Mapping(target = "enable_keyboard_navigation", constant = "true")
    @Mapping(target = "enable_animated_images", constant = "true")
    @Mapping(target = "blur_nsfw", constant = "true")
    @Mapping(target = "auto_expand", constant = "true")
    @Mapping(target = "admin", constant = "true")
    @Mapping(target = "person_id", source = "person.id")
    @Mapping(target = "email", source = "person.email")
    @Mapping(target = "theme", source = "person.defaultTheme")
    @Mapping(target = "validator_time", constant = "2023-06-09T02:35:26.397746Z", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "totp_2fa_url", constant = "")
    @Mapping(target = "interface_language", source = "person.interfaceLanguage")
    @Mapping(target = "default_sort_type", source = "person.defaultSortType")
    @Mapping(target = "default_listing_type", source = "person.defaultListingType")
    @Mapping(target = "show_scores", source = "person.showScores")
    @Mapping(target = "show_read_posts", source = "person.showReadPosts")
    @Mapping(target = "show_nsfw", source = "person.showNsfw")
    @Mapping(target = "show_new_post_notifs", source = "person.showNewPostNotifications")
    @Mapping(target = "show_bot_accounts", source = "person.showBotAccounts")
    @Mapping(target = "show_avatars", source = "person.showAvatars")
    @Mapping(target = "send_notifications_to_email", source = "person.sendNotificationsToEmail")
    @Mapping(target = "open_links_in_new_tab", source = "person.openLinksInNewTab")
    @Mapping(target = "email_verified", source = "person.emailVerified")
    @Mapping(target = "accepted_application", constant = "true")
    LocalUser convert(@Nullable Person person);
}
