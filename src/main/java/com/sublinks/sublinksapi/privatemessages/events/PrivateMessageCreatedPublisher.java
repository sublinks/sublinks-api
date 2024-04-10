package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PrivateMessageCreatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PrivateMessage privateMessage) {

    final PrivateMessageCreatedEvent privateMessageCreatedEvent = new PrivateMessageCreatedEvent(
        this, privateMessage);
    applicationEventPublisher.publishEvent(privateMessageCreatedEvent);
  }
}
