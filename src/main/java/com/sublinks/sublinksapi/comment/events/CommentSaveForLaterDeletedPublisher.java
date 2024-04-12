package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentSaveForLater;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSaveForLaterDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentSaveForLater commentSaveForLater) {

    final CommentSaveForLaterDeletedEvent commentSaveForLaterDeletedEvent = new CommentSaveForLaterDeletedEvent(
        this, commentSaveForLater);
    applicationEventPublisher.publishEvent(commentSaveForLaterDeletedEvent);
  }
}
