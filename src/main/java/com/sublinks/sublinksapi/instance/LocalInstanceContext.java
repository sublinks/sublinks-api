package com.sublinks.sublinksapi.instance;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.instance.dto.InstanceAggregate;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
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
