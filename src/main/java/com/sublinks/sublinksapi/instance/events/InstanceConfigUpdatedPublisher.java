package com.sublinks.sublinksapi.instance.events;

import com.sublinks.sublinksapi.instance.entities.InstanceConfig;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InstanceConfigUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public InstanceConfigUpdatedPublisher(ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(InstanceConfig instanceConfig) {

    InstanceConfigUpdatedEvent instanceConfigUpdatedEvent = new InstanceConfigUpdatedEvent(this,
        instanceConfig);
    applicationEventPublisher.publishEvent(instanceConfigUpdatedEvent);
  }
}
