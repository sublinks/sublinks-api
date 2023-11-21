package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(CommentLike commentLike) {

    final CommentLikeCreatedEvent commentLikeCreatedEvent = new CommentLikeCreatedEvent(this,
        commentLike);
    applicationEventPublisher.publishEvent(commentLikeCreatedEvent);
  }
}
