package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.utils.TotpUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    imports = {TotpUtil.class, DateUtils.class}
)
public interface LocalUserMapper extends Converter<Person, LocalUser> {

  @Override
  @Mapping(target = "totp_2fa_enabled", expression = "java(person.getTotpVerifiedSecret() != null)")
  @Mapping(target = "post_listing_mode", source = "person.postListingType")
  @Mapping(target = "infinite_scroll_enabled", source = "person.infiniteScroll")
  @Mapping(target = "enable_keyboard_navigation", source = "person.keyboardNavigation")
  @Mapping(target = "enable_animated_images", source = "person.animatedImages")
  @Mapping(target = "blur_nsfw", source = "person.blurNsfw")
  @Mapping(target = "collapse_bot_comments", source = "person.collapseBotComments")
  @Mapping(target = "auto_expand", source = "person.autoExpanding")
  @Mapping(target = "admin", source = "person", qualifiedByName = "is_admin")
  @Mapping(target = "person_id", source = "person.id")
  @Mapping(target = "email", source = "person.email")
  @Mapping(target = "theme", source = "person.defaultTheme")
  @Mapping(target = "validator_time", constant = "2023-06-09T02:35:26.397746Z",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "totp_2fa_url", expression = "java(TotpUtil.createUriString(person))")
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
  LocalUser convert(@Nullable Person person);


  @Named("is_admin")
  default boolean isAdmin(Person person) {

    return RoleAuthorizingService.isAdmin(person);
  }
}
