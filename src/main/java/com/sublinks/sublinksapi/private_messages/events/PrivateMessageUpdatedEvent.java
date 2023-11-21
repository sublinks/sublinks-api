package com.sublinks.sublinksapi.private_messages.events;

import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PrivateMessageUpdatedEvent extends ApplicationEvent {

  private final PrivateMessage privateMessage;

  public PrivateMessageUpdatedEvent(final Object source, final PrivateMessage privateMessage) {

    super(source);
    this.privateMessage = privateMessage;
  }
}
