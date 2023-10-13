package com.fedilinks.fedilinksapi.instance;

import org.springframework.stereotype.Component;

@Component
public class InstanceService {

    private final InstanceRepository instanceRepository;

    public InstanceService(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    public Instance create(Instance instance) {
        return instanceRepository.saveAndFlush(instance);
    }

    public Instance update(Instance instance) {
        return instanceRepository.saveAndFlush(instance);
    }
}
