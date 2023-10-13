package com.fedilinks.fedilinksapi.instance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record InstanceSettings(
        @Value("${fedilinks.settings.base_url}") String baseUrl,
        @Value("${fedilinks.settings.enable_down_votes}") Boolean enableDownVotes,
        @Value("${fedilinks.settings.enable_nsfw}") Boolean enableNsfw,
        @Value("${fedilinks.settings.hide_modlog_mod_names}") Boolean hideModlogModNames,
        @Value("${fedilinks.settings.actor_name_max_length}") Integer actorNameMaxLength,
        @Value("${fedilinks.settings.private_instance}") Boolean isPrivateInstance
) { }
