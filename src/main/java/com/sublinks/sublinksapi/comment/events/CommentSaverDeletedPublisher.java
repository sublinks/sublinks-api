package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentSave;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSaverDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentSave commentSave) {

    final CommentSaveDeletedEvent commentSaveDeletedEvent = new CommentSaveDeletedEvent(this,
        commentSave);
    applicationEventPublisher.publishEvent(commentSaveDeletedEvent);
  }
}
