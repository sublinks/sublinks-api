package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.api.lemmy.v3.user.enums.PostListingMode;
import java.util.Collection;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record SaveUserSettings(
    Boolean show_nsfw,
    Boolean show_scores,
    String theme,
    SortType default_sort_type,
    ListingType default_listing_type,
    String interface_language,
    String avatar,
    String banner,
    String display_name,
    String email,
    String bio,
    String matrix_user_id,
    Boolean show_avatars,
    Boolean send_notifications_to_email,
    Boolean bot_account,
    Boolean show_bot_accounts,
    Boolean show_read_posts,
    Collection<String> discussion_languages,
    Boolean open_links_in_new_tab,
    Boolean auto_expand,
    Boolean blur_nsfw,
    Boolean collapse_bot_comments,
    Boolean enable_keyboard_navigation,
    Boolean infinite_scroll_enabled,
    Boolean enable_animated_images,
    PostListingMode post_listing_mode
) {

}