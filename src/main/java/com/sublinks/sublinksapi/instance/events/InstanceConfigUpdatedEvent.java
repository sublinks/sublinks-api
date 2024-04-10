package com.sublinks.sublinksapi.instance.events;

import com.sublinks.sublinksapi.instance.entities.InstanceConfig;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InstanceConfigUpdatedEvent extends ApplicationEvent {

  private final InstanceConfig instanceConfig;

  public InstanceConfigUpdatedEvent(Object source, InstanceConfig instanceConfig) {

    super(source);
    this.instanceConfig = instanceConfig;
  }
}
