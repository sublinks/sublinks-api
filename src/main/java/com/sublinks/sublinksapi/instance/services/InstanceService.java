package com.sublinks.sublinksapi.instance.services;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.instance.repositories.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import com.sublinks.sublinksapi.utils.KeyService;
import com.sublinks.sublinksapi.utils.KeyStore;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InstanceService {

    private final InstanceRepository instanceRepository;
    private final InstanceAggregateRepository instanceAggregateRepository;

    private final KeyService keyService;

    @Transactional
    public void createInstance(@NotNull Instance instance) {

        KeyStore keys = keyService.generate();
        instance.setPublicKey(keys.publicKey());
        instance.setPrivateKey(keys.privateKey());
        instanceRepository.save(instance);
    }

    @Transactional
    public void updateInstance(@NotNull Instance instance) {

        instanceRepository.save(instance);
    }
}
