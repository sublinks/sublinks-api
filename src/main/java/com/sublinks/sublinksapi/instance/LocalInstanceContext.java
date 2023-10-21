package com.sublinks.sublinksapi.instance;

import com.sublinks.sublinksapi.language.LanguageRepository;
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
