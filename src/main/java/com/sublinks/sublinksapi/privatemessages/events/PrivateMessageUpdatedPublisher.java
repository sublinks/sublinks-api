package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessage;
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
