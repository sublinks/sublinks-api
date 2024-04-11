package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final Comment comment) {

    final CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(this, comment);
    applicationEventPublisher.publishEvent(commentCreatedEvent);
  }
}
