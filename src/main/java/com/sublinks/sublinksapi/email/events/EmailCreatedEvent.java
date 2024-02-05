package com.sublinks.sublinksapi.email.events;

import com.sublinks.sublinksapi.email.models.CreateEmailEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailCreatedEvent extends ApplicationEvent {

  private final CreateEmailEvent event;

  public EmailCreatedEvent(final Object source, final CreateEmailEvent event) {

    super(source);
    this.event = event;
  }
}
