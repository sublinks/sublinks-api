package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.Comment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CommentDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public CommentDeletedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(Comment comment) {

    CommentDeletedEvent commentDeletedEvent = new CommentDeletedEvent(this, comment);
    applicationEventPublisher.publishEvent(commentDeletedEvent);
  }
}
