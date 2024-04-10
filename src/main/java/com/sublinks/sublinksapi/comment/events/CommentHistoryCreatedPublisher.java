package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentHistory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CommentHistoryCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public CommentHistoryCreatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(CommentHistory commentHistory) {

    CommentHistoryCreatedEvent commentHistoryCreatedEvent = new CommentHistoryCreatedEvent(this,
        commentHistory);
    applicationEventPublisher.publishEvent(commentHistoryCreatedEvent);
  }
}
