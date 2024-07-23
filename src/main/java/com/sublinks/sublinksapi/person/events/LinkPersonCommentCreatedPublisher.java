package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkPersonCommentCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final LinkPersonComment linkPersonComment) {

    final LinkPersonCommentCreatedEvent linkPersonCommentCreatedEvent = new LinkPersonCommentCreatedEvent(
        this, linkPersonComment);
    applicationEventPublisher.publishEvent(linkPersonCommentCreatedEvent);
  }
}
