package com.sublinks.sublinksapi.moderation.events;

import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Moderation Log Created Publisher
 */
@Component
@RequiredArgsConstructor
public class ModerationLogCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  /**
   * Publishes a ModerationLogCreatedEvent event
   *
   * @param moderationLog a ModerationLog
   */
  public void publish(final ModerationLog moderationLog) {

    final ModerationLogCreatedEvent moderationLogCreatedEvent = new ModerationLogCreatedEvent(this,
        moderationLog);
    applicationEventPublisher.publishEvent(moderationLogCreatedEvent);
  }
}