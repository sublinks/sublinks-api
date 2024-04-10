package com.sublinks.sublinksapi.moderation.events;

import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Moderation Log Created Event
 */
@Getter
public class ModerationLogCreatedEvent extends ApplicationEvent {

  private final ModerationLog moderationLog;

  public ModerationLogCreatedEvent(final Object source, final ModerationLog moderationLog) {

    super(source);
    this.moderationLog = moderationLog;
  }
}