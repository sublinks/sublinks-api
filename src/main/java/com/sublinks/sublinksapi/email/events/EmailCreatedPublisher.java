package com.sublinks.sublinksapi.email.events;

import com.sublinks.sublinksapi.email.models.CreateEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CreateEmailEvent event) {

    final EmailCreatedEvent commentCreatedEvent = new EmailCreatedEvent(this, event);
    applicationEventPublisher.publishEvent(commentCreatedEvent);
  }
}
