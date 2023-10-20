package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.ListingType;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

import java.util.Collection;

@Builder
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
        Boolean show_new_post_notifs,
        Collection<String> discussion_languages,
        Boolean generate_totp_2fa,
        Boolean open_links_in_new_tab
) {
}