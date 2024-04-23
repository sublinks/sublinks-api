package com.sublinks.sublinksapi.instance.models;

import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.instance.entities.InstanceAggregate;
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
