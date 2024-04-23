package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentSave;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSaveCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentSave commentSave) {

    final CommentSaveCreatedEvent commentSaveEvent = new CommentSaveCreatedEvent(
        this, commentSave);
    applicationEventPublisher.publishEvent(commentSaveEvent);
  }
}
