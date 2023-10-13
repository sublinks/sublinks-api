package com.fedilinks.fedilinksapi.instance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@ComponentScan()
public class Config {
    private final Long localInstanceId;

    public Config(@Value("${fedilinks.settings.local_instance_id:1}") Long localInstanceId) {
        this.localInstanceId = localInstanceId;
    }

    @Bean
    Instance localInstance(InstanceRepository instanceRepository) {
        final Optional<Instance> instance = instanceRepository.findById(localInstanceId);
        return instance.orElseGet(() -> Instance.builder().build());
    }

    @Bean
    InstanceAggregate localInstanceAggregate(InstanceAggregateRepository aggregateRepository) {
        final Optional<InstanceAggregate> localInstanceAggregate = aggregateRepository.findById(localInstanceId);
        return localInstanceAggregate.orElseGet(() -> InstanceAggregate.builder().build());
    }
}
