package com.sublinks.sublinksapi.instance;

import com.sublinks.sublinksapi.util.KeyService;
import com.sublinks.sublinksapi.util.KeyStore;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class InstanceService {

    private final InstanceRepository instanceRepository;
    private final InstanceAggregateRepository instanceAggregateRepository;

    private final KeyService keyService;

    public InstanceService(
            final InstanceRepository instanceRepository,
            final InstanceAggregateRepository instanceAggregateRepository,
            final KeyService keyService
    ) {
        this.instanceRepository = instanceRepository;
        this.instanceAggregateRepository = instanceAggregateRepository;
        this.keyService = keyService;
    }

    public void createInstance(@NotNull Instance instance) {

        KeyStore keys = keyService.generate();
        instance.setPublicKey(keys.publicKey());
        instance.setPrivateKey(keys.privateKey());
        instanceRepository.save(instance);
        InstanceAggregate instanceAggregate = InstanceAggregate.builder().instance(instance).build();
        instanceAggregateRepository.save(instanceAggregate);
    }

    public void updateInstance(@NotNull Instance instance) {

        instanceRepository.save(instance);
    }
}
