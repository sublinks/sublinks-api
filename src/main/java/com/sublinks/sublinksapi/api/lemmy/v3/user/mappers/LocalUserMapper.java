package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AdminBooleanMapper.class})
public interface LocalUserMapper extends Converter<Person, LocalUser> {

  @Override
  @Mapping(target = "totp_2fa_enabled", constant = "true")
  @Mapping(target = "post_listing_mode", source = "person.postListingType")
  @Mapping(target = "infinite_scroll_enabled", source = "person.isInfiniteScroll")
  @Mapping(target = "enable_keyboard_navigation", source = "person.isKeyboardNavigation")
  @Mapping(target = "enable_animated_images", source = "person.isAnimatedImages")
  @Mapping(target = "blur_nsfw", source = "person.isBlurNsfw")
  @Mapping(target = "collapse_bot_comments", source = "person.collapseBotComments")
  @Mapping(target = "auto_expand", source = "person.isAutoExpanding")
  @Mapping(target = "admin", source = "person")
  @Mapping(target = "person_id", source = "person.id")
  @Mapping(target = "email", source = "person.email")
  @Mapping(target = "theme", source = "person.defaultTheme")
  @Mapping(target = "validator_time", constant = "2023-06-09T02:35:26.397746Z",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "totp_2fa_url", constant = "")
  @Mapping(target = "interface_language", source = "person.interfaceLanguage")
  @Mapping(target = "default_sort_type", source = "person.defaultSortType")
  @Mapping(target = "default_listing_type", source = "person.defaultListingType")
  @Mapping(target = "show_scores", source = "person.showScores")
  @Mapping(target = "show_read_posts", source = "person.showReadPosts")
  @Mapping(target = "show_nsfw", source = "person.showNsfw")
  @Mapping(target = "show_bot_accounts", source = "person.showBotAccounts")
  @Mapping(target = "show_avatars", source = "person.showAvatars")
  @Mapping(target = "send_notifications_to_email", source = "person.sendNotificationsToEmail")
  @Mapping(target = "open_links_in_new_tab", source = "person.openLinksInNewTab")
  @Mapping(target = "email_verified", source = "person.emailVerified")
  @Mapping(target = "accepted_application", constant = "true")
  @Mapping(target = "matrix_user_id", source = "person.matrixUserId")
  LocalUser convert(@Nullable Person person);
}
