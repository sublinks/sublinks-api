package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.api.lemmy.v3.user.enums.PostListingMode;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record LocalUser(
    Long id,
    Long person_id,
    String email,
    boolean show_nsfw,
    String theme,
    SortType default_sort_type,
    ListingType default_listing_type,
    String interface_language,
    boolean show_avatars,
    boolean send_notifications_to_email,
    String validator_time,
    boolean show_scores,
    boolean show_bot_accounts,
    boolean show_read_posts,
    boolean show_new_post_notifs,
    boolean email_verified,
    boolean accepted_application,
    String totp_2fa_url,
    boolean open_links_in_new_tab,
    boolean blur_nsfw,
    boolean auto_expand,
    boolean infinite_scroll_enabled,
    boolean admin,
    PostListingMode post_listing_mode,
    boolean totp_2fa_enabled,
    boolean enable_keyboard_navigation,
    boolean enable_animated_images
) {

}