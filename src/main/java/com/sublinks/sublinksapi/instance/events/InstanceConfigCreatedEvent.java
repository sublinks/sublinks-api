package com.sublinks.sublinksapi.instance.events;

import com.sublinks.sublinksapi.instance.dto.InstanceConfig;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InstanceConfigCreatedEvent extends ApplicationEvent {

  private final InstanceConfig instanceConfig;

  public InstanceConfigCreatedEvent(Object source, InstanceConfig instanceConfig) {

    super(source);
    this.instanceConfig = instanceConfig;
  }
}
