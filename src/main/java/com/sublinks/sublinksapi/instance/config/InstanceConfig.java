package com.sublinks.sublinksapi.instance.config;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.instance.dto.InstanceAggregate;
import com.sublinks.sublinksapi.instance.repositories.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@ComponentScan()
public class InstanceConfig {
    private final Long localInstanceId;

    public InstanceConfig(@Value("${sublinks.settings.local_instance_id:1}") final Long localInstanceId) {

        this.localInstanceId = localInstanceId;
    }

    @Bean
    Instance localInstance(final InstanceRepository instanceRepository) {

        final Optional<Instance> instance = instanceRepository.findById(localInstanceId);
        return instance.orElseGet(() -> Instance.builder().build());
    }

    @Bean
    InstanceAggregate localInstanceAggregate(final InstanceAggregateRepository aggregateRepository) {

        final Optional<InstanceAggregate> localInstanceAggregate = aggregateRepository.findById(localInstanceId);
        return localInstanceAggregate.orElseGet(() -> InstanceAggregate.builder().build());
    }
}
