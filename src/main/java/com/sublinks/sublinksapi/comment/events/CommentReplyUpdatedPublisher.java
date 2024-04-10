package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReplyUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentReply commentReply) {

    final CommentReplyUpdatedEvent commentReplyUpdatedEvent = new CommentReplyUpdatedEvent(this,
        commentReply);
    applicationEventPublisher.publishEvent(commentReplyUpdatedEvent);
  }
}
