package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.api.lemmy.v3.user.enums.PostListingMode;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.dto.Person;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class LocalUserMapperImpl implements LocalUserMapper {

  private final ConversionService conversionService;
  private final RoleAuthorizingService roleAuthorizingService;

  public LocalUser convert(@Nullable Person person) {

    if (person == null) {
      return null;
    }

    LocalUser.LocalUserBuilder localUser = LocalUser.builder();

    localUser.person_id(person.getId());

    localUser.email(person.getEmail());
    localUser.email_verified(person.isEmailVerified());
    
    localUser.post_listing_mode(
        conversionService.convert(person.getPostListingType(), PostListingMode.class));
    localUser.infinite_scroll_enabled(person.isInfiniteScroll());
    localUser.enable_keyboard_navigation(person.isKeyboardNavigation());
    localUser.enable_animated_images(person.isAnimatedImages());
    localUser.blur_nsfw(person.isBlurNsfw());
    localUser.collapse_bot_comments(person.isCollapseBotComments());
    localUser.auto_expand(person.isAutoExpanding());
    localUser.admin(roleAuthorizingService.isAdmin(person));
    localUser.theme(person.getDefaultTheme());
    localUser.interface_language(person.getInterfaceLanguage());
    localUser.default_sort_type(
        conversionService.convert(person.getDefaultSortType(), SortType.class));
    localUser.default_listing_type(
        conversionService.convert(person.getDefaultListingType(), ListingType.class));
    localUser.show_scores(person.isShowScores());
    localUser.show_read_posts(person.isShowReadPosts());
    localUser.show_nsfw(person.isShowNsfw());
    localUser.show_bot_accounts(person.isShowBotAccounts());
    localUser.show_avatars(person.isShowAvatars());
    localUser.send_notifications_to_email(person.isSendNotificationsToEmail());
    localUser.open_links_in_new_tab(person.isOpenLinksInNewTab());
    localUser.id(person.getId());

    localUser.totp_2fa_enabled(true);
    // @todo: implement validator_time
    localUser.validator_time("2023-06-09T02:35:26.397746Z");
    // @todo: implement totp_2fa_url
    localUser.totp_2fa_url("");
    localUser.accepted_application(true);

    return localUser.build();
  }
}
