package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentReplyUpdatedEvent extends ApplicationEvent {

  private final CommentReply commentReply;

  public CommentReplyUpdatedEvent(final Object source, final CommentReply commentReply) {

    super(source);
    this.commentReply = commentReply;
  }
}
