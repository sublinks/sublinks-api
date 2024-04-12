package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentSaveForLater;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSaveForLaterCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentSaveForLater commentSaveForLater) {

    final CommentSaveForLaterCreatedEvent commentSaveForLaterCreatedEvent = new CommentSaveForLaterCreatedEvent(
        this, commentSaveForLater);
    applicationEventPublisher.publishEvent(commentSaveForLaterCreatedEvent);
  }
}
