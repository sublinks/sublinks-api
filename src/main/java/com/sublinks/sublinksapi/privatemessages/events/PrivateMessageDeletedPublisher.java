package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PrivateMessageDeletedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PrivateMessage privateMessage) {

    final PrivateMessageDeletedEvent privateMessageDeletedEvent = new PrivateMessageDeletedEvent(
        this, privateMessage);
    applicationEventPublisher.publishEvent(privateMessageDeletedEvent);
  }
}
