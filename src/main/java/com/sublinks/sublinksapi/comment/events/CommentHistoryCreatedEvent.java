package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentHistory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentHistoryCreatedEvent extends ApplicationEvent {

  private final CommentHistory commentHistory;

  public CommentHistoryCreatedEvent(final Object source, final CommentHistory commentHistory) {

    super(source);
    this.commentHistory = commentHistory;
  }
}
