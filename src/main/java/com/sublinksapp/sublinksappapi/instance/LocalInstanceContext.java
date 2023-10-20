package com.sublinksapp.sublinksappapi.instance;

import com.sublinksapp.sublinksappapi.language.LanguageRepository;
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
