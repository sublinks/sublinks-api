package com.sublinks.sublinksapi.instance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record InstanceSettings(
        @Value("${sublinks.settings.base_url}") String baseUrl,
        @Value("${sublinks.settings.enable_down_votes}") Boolean enableDownVotes,
        @Value("${sublinks.settings.enable_nsfw}") Boolean enableNsfw,
        @Value("${sublinks.settings.hide_modlog_mod_names}") Boolean hideModlogModNames,
        @Value("${sublinks.settings.actor_name_max_length}") Integer actorNameMaxLength,
        @Value("${sublinks.settings.private_instance}") Boolean isPrivateInstance
) {
}
