package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkPersonCommentUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final LinkPersonComment linkPersonComment) {

    final LinkPersonCommentUpdatedEvent linkPersonCommentUpdatedEvent = new LinkPersonCommentUpdatedEvent(
        this, linkPersonComment);
    applicationEventPublisher.publishEvent(linkPersonCommentUpdatedEvent);
  }
}
