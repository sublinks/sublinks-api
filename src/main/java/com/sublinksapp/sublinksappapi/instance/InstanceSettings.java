package com.sublinksapp.sublinksappapi.instance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record InstanceSettings(
        @Value("${sublinksapp.settings.base_url}") String baseUrl,
        @Value("${sublinksapp.settings.enable_down_votes}") Boolean enableDownVotes,
        @Value("${sublinksapp.settings.enable_nsfw}") Boolean enableNsfw,
        @Value("${sublinksapp.settings.hide_modlog_mod_names}") Boolean hideModlogModNames,
        @Value("${sublinksapp.settings.actor_name_max_length}") Integer actorNameMaxLength,
        @Value("${sublinksapp.settings.private_instance}") Boolean isPrivateInstance
) {
}
