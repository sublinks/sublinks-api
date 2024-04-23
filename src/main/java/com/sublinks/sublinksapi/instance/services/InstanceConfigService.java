package com.sublinks.sublinksapi.instance.services;

import com.sublinks.sublinksapi.instance.entities.InstanceConfig;
import com.sublinks.sublinksapi.instance.events.InstanceConfigCreatedPublisher;
import com.sublinks.sublinksapi.instance.events.InstanceConfigUpdatedPublisher;
import com.sublinks.sublinksapi.instance.repositories.InstanceConfigRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InstanceConfigService {

  private final InstanceConfigRepository instanceConfigRepository;
  private final InstanceConfigCreatedPublisher instanceConfigCreatedPublisher;
  private final InstanceConfigUpdatedPublisher instanceConfigUpdatedPublisher;

  @Transactional
  public void createInstanceConfig(@NotNull InstanceConfig instance) {

    instanceConfigRepository.save(instance);
    instanceConfigCreatedPublisher.publish(instance);
  }

  @Transactional
  public void updateInstanceConfig(@NotNull InstanceConfig instance) {

    instanceConfigRepository.save(instance);
    instanceConfigUpdatedPublisher.publish(instance);
  }
}
