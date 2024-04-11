package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReplyCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentReply commentReply) {

    final CommentReplyCreatedEvent commentReplyCreatedEvent = new CommentReplyCreatedEvent(this,
        commentReply);
    applicationEventPublisher.publishEvent(commentReplyCreatedEvent);
  }
}
