package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSaveCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final LinkPersonComment linkPersonComment) {

    final CommentSaveCreatedEvent commentSaveEvent = new CommentSaveCreatedEvent(
        this, linkPersonComment);
    applicationEventPublisher.publishEvent(commentSaveEvent);
  }
}
