package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.ListingType;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

import java.util.Collection;

@Builder
public record SaveUserSettings(
        boolean show_nsfw,
        boolean show_scores,
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
        boolean show_avatars,
        boolean send_notifications_to_email,
        boolean bot_account,
        boolean show_bot_accounts,
        boolean show_read_posts,
        boolean show_new_post_notifs,
        Collection<String> discussion_languages,
        boolean generate_totp_2fa,
        String auth,
        boolean open_links_in_new_tab
) {
}