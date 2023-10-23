package com.sublinks.sublinksapi.api.lemmy.v3.site;

import com.sublinks.sublinksapi.api.lemmy.v3.models.LocalSite;
import com.sublinks.sublinksapi.api.lemmy.v3.models.LocalSiteRateLimit;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Site;
import com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates.SiteAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.SiteView;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocalInstanceContextMapper {

    @Mapping(target = "users_active_week", source = "context.instanceAggregate.activeWeeklyUserCount")
    @Mapping(target = "users_active_month", source = "context.instanceAggregate.activeMonthlyUserCount")
    @Mapping(target = "users_active_half_year", source = "context.instanceAggregate.activeHalfYearUserCount")
    @Mapping(target = "users_active_day", source = "context.instanceAggregate.activeDailyUserCount")
    @Mapping(target = "users", source = "context.instanceAggregate.userCount")
    @Mapping(target = "site_id", source = "context.instance.id")
    @Mapping(target = "posts", source = "context.instanceAggregate.postCount")
    @Mapping(target = "id", source = "context.instance.id")
    @Mapping(target = "communities", source = "context.instanceAggregate.communityCount")
    @Mapping(target = "comments", source = "context.instanceAggregate.commentCount")
    SiteAggregates LocalInstanceContextToSiteAggregates(LocalInstanceContext context);


    @Mapping(target = "id", source = "context.instance.id")
    @Mapping(target = "local_site_id", source = "context.instance.id")
    @Mapping(target = "updated", source = "context.instance.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "published", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "search_per_second", source = "context.rateLimits.searchPerSecond")
    @Mapping(target = "search", source = "context.rateLimits.search")
    @Mapping(target = "register_per_second", source = "context.rateLimits.registerPerSecond")
    @Mapping(target = "register", source = "context.rateLimits.register")
    @Mapping(target = "post_per_second", source = "context.rateLimits.postPerSecond")
    @Mapping(target = "post", source = "context.rateLimits.post")
    @Mapping(target = "message_per_second", source = "context.rateLimits.messagePerSecond")
    @Mapping(target = "message", source = "context.rateLimits.message")
    @Mapping(target = "image_per_second", source = "context.rateLimits.imagePerSecond")
    @Mapping(target = "image", source = "context.rateLimits.image")
    @Mapping(target = "comment_per_second", source = "context.rateLimits.commentPerSecond")
    @Mapping(target = "comment", source = "context.rateLimits.comment")
    LocalSiteRateLimit LocalInstanceContextToLocalSiteRateLimit(LocalInstanceContext context);

    @Mapping(target = "site", source = "context")
    @Mapping(target = "local_site", source = "context")
    @Mapping(target = "counts", source = "context")
    @Mapping(target = "local_site_rate_limit", source = "context")
    SiteView LocalInstanceContextToSiteView(LocalInstanceContext context);


    @Mapping(target = "id", source = "context.instance.id")
    @Mapping(target = "name", source = "context.instance.name")
    @Mapping(target = "sidebar", source = "context.instance.sidebar")
    @Mapping(target = "published", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "updated", source = "context.instance.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "icon", source = "context.instance.iconUrl")
    @Mapping(target = "banner", source = "context.instance.bannerUrl")
    @Mapping(target = "description", source = "context.instance.description")
    @Mapping(target = "actor_id", source = "context.instance.domain")
    @Mapping(target = "last_refreshed_at", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "inbox_url", source = "context.instance.domain")
    @Mapping(target = "public_key", source = "context.instance.publicKey")
    @Mapping(target = "instance_id", source = "context.instance.id")
    Site LocalInstanceContextToSite(LocalInstanceContext context);

    @Mapping(target = "id", source = "context.instance.id")
    @Mapping(target = "site_id", source = "context.instance.id")
    @Mapping(target = "site_setup", expression = "java(!context.instance().getDomain().isEmpty())")
    @Mapping(target = "enable_downvotes", source = "context.settings.enableDownVotes")
    @Mapping(target = "enable_nsfw", source = "context.settings.enableNsfw")
    @Mapping(target = "published", source = "context.instance.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "updated", source = "context.instance.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "actor_name_max_length", source = "context.settings.actorNameMaxLength")
    @Mapping(target = "private_instance", source = "context.settings.isPrivateInstance")
    @Mapping(target = "community_creation_admin_only", constant = "false")
    @Mapping(target = "require_email_verification", constant = "false")
    @Mapping(target = "application_question", constant = "")
    @Mapping(target = "default_theme", constant = "")
    @Mapping(target = "default_post_listing_type", constant = "All")
    @Mapping(target = "legal_information", constant = "")
    @Mapping(target = "hide_modlog_mod_names", constant = "false")
    @Mapping(target = "application_email_admins", constant = "false")
    @Mapping(target = "slur_filter_regex", constant = "")
    @Mapping(target = "federation_enabled", constant = "false")
    @Mapping(target = "captcha_enabled", constant = "false")
    @Mapping(target = "captcha_difficulty", constant = "")
    @Mapping(target = "registration_mode", constant = "Open")
    @Mapping(target = "reports_email_admins", constant = "false")
    LocalSite LocalInstanceContextToLocalSite(LocalInstanceContext context);
}
