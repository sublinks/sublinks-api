package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSaverDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final LinkPersonComment linkPersonComment) {

    final CommentSaveDeletedEvent commentSaveDeletedEvent = new CommentSaveDeletedEvent(this,
            linkPersonComment);
    applicationEventPublisher.publishEvent(commentSaveDeletedEvent);
  }
}
