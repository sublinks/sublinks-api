package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentLike commentLike, final CommentLikeUpdatedEvent.Action action) {

    final CommentLikeUpdatedEvent commentLikeUpdatedEvent = new CommentLikeUpdatedEvent(this,
        commentLike, action);
    applicationEventPublisher.publishEvent(commentLikeUpdatedEvent);
  }
}
