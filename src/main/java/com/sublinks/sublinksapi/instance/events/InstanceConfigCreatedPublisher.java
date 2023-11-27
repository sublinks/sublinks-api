package com.sublinks.sublinksapi.instance.events;

import com.sublinks.sublinksapi.instance.dto.InstanceConfig;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InstanceConfigCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public InstanceConfigCreatedPublisher(ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(InstanceConfig instanceConfig) {

    InstanceConfigCreatedEvent instanceConfigCreatedEvent = new InstanceConfigCreatedEvent(this,
        instanceConfig);
    applicationEventPublisher.publishEvent(instanceConfigCreatedEvent);
  }
}
