package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessageReport;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageReportCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PrivateMessageReportCreatedPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PrivateMessageReport privateMessageReport) {

    final PrivateMessageReportCreatedEvent privateMessageUpdatedEvent = new PrivateMessageReportCreatedEvent(
        this, privateMessageReport);
    applicationEventPublisher.publishEvent(privateMessageUpdatedEvent);
  }
}
