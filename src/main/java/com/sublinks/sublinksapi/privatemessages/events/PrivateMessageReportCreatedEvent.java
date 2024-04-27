package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessageReport;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PrivateMessageReportCreatedEvent extends ApplicationEvent {

  private final PrivateMessageReport privateMessageReport;

  public PrivateMessageReportCreatedEvent(final Object source,
      final PrivateMessageReport privateMessageReport) {

    super(source);
    this.privateMessageReport = privateMessageReport;
  }
}
