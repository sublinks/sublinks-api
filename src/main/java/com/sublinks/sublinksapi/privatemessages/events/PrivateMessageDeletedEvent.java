package com.sublinks.sublinksapi.privatemessages.events;

import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PrivateMessageDeletedEvent extends ApplicationEvent {

  private final PrivateMessage privateMessage;

  public PrivateMessageDeletedEvent(final Object source, final PrivateMessage privateMessage) {

    super(source);
    this.privateMessage = privateMessage;
  }
}
