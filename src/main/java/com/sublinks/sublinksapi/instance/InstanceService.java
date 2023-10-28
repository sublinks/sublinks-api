package com.sublinks.sublinksapi.instance;

import org.springframework.stereotype.Component;

@Component
public class InstanceService {

    private final InstanceRepository instanceRepository;
    private final InstanceAggregateRepository instanceAggregateRepository;

    public InstanceService(
            InstanceRepository instanceRepository,
            InstanceAggregateRepository instanceAggregateRepository
    ) {
        this.instanceRepository = instanceRepository;
        this.instanceAggregateRepository = instanceAggregateRepository;
    }

    public void createInstance(Instance instance) {
        instanceRepository.save(instance);
        InstanceAggregate instanceAggregate = InstanceAggregate.builder().instance(instance).build();
        instanceAggregateRepository.save(instanceAggregate);
    }

    public void updateInstance(Instance instance) {
        instanceRepository.save(instance);
    }

    public InstanceAggregate getInstanceAggregate(Instance instance) {
        InstanceAggregate instanceAggregate = instance.getInstanceAggregate();
        if (instanceAggregate == null) {
            instanceAggregate = InstanceAggregate.builder().instance(instance).build();
        }
        return instanceAggregate;
    }
}
