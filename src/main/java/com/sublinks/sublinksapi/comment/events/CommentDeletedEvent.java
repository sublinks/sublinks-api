package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentDeletedEvent extends ApplicationEvent {

  private final Comment comment;

  public CommentDeletedEvent(final Object source, final Comment comment) {

    super(source);
    this.comment = comment;
  }
}
