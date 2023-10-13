package com.fedilinks.fedilinksapi.instance;

import com.fedilinks.fedilinksapi.language.LanguageRepository;
import org.springframework.stereotype.Component;

@Component
public record LocalInstanceContext(
        Instance instance,
        InstanceAggregate instanceAggregate,
        LanguageRepository languageRepository,
        InstanceSettings settings,
        InstanceRateLimits rateLimits
) {
}
