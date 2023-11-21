package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import java.util.Collection;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CreateSite(

    String name,
    String sidebar,
    String description,
    String icon,
    String banner,
    Boolean enable_downvotes,
    Boolean enable_nsfw,
    Boolean community_creation_admin_only,
    Boolean require_email_verification,
    String application_question,
    Boolean private_instance,
    String default_theme,
    String default_post_listing_type,
    String legal_information,
    Boolean application_email_admins,
    Boolean hide_modlog_mod_names,
    Collection<String> discussion_languages,
    String slur_filter_regex,
    Integer actor_name_max_length,
    Integer rate_limit_message,
    Integer rate_limit_message_per_second,
    Integer rate_limit_post,
    Integer rate_limit_post_per_second,
    Integer rate_limit_register,
    Integer rate_limit_register_per_second,
    Integer rate_limit_image,
    Integer rate_limit_image_per_second,
    Integer rate_limit_comment,
    Integer rate_limit_comment_per_second,
    Integer rate_limit_search,
    Integer rate_limit_search_per_second,
    Boolean federation_enabled,
    Boolean federation_debug,
    Boolean captcha_enabled,
    String captcha_difficulty,
    Collection<String> allowed_instances,
    Collection<String> blocked_instances,
    Collection<String> taglines,
    String registration_mode
) {

}