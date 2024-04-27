package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessageReport;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageReportUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PrivateMessageReportUpdatedPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PrivateMessageReport privateMessageReport) {

    final PrivateMessageReportUpdatedEvent privateMessageUpdatedEvent = new PrivateMessageReportUpdatedEvent(
        this, privateMessageReport);
    applicationEventPublisher.publishEvent(privateMessageUpdatedEvent);
  }
}
