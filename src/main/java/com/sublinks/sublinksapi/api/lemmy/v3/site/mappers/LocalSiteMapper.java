package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.LocalSite;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocalSiteMapper extends Converter<LocalInstanceContext, LocalSite> {

  @Override
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
  LocalSite convert(@Nullable LocalInstanceContext context);
}
