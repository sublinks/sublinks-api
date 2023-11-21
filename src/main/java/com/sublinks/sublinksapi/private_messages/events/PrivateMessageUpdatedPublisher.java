package com.sublinks.sublinksapi.private_messages.events;

import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PrivateMessageUpdatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PrivateMessage privateMessage) {

    final PrivateMessageUpdatedEvent privateMessageUpdatedEvent = new PrivateMessageUpdatedEvent(
        this, privateMessage);
    applicationEventPublisher.publishEvent(privateMessageUpdatedEvent);
  }
}
