package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkPersonCommentDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final LinkPersonComment linkPersonComment) {

    final LinkPersonCommentDeletedEvent linkPersonCommentDeletedEvent = new LinkPersonCommentDeletedEvent(this,
            linkPersonComment);
    applicationEventPublisher.publishEvent(linkPersonCommentDeletedEvent);
  }
}
