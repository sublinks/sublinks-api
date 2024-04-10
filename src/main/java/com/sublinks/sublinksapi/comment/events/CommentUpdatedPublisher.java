package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final Comment comment) {

    final CommentUpdatedEvent commentUpdatedEvent = new CommentUpdatedEvent(this, comment);
    applicationEventPublisher.publishEvent(commentUpdatedEvent);
  }
}
