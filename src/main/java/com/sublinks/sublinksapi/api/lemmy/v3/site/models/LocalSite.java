package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.RegistrationMode;
import lombok.Builder;

@Builder
public record LocalSite(
    Long id,
    Long site_id,
    boolean site_setup,
    boolean enable_downvotes,
    boolean enable_nsfw,
    boolean community_creation_admin_only,
    boolean require_email_verification,
    String application_question,
    boolean private_instance,
    String default_theme,
    ListingType default_post_listing_type,
    String legal_information,
    boolean hide_modlog_mod_names,
    boolean application_email_admins,
    String slur_filter_regex,
    int actor_name_max_length,
    boolean federation_enabled,
    boolean captcha_enabled,
    String captcha_difficulty,
    String published,
    String updated,
    RegistrationMode registration_mode,
    boolean reports_email_admins
) {

}