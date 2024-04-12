package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentSaveForLater;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentSaveForLaterCreatedEvent extends ApplicationEvent {

  private final CommentSaveForLater commentSaveForLater;

  public CommentSaveForLaterCreatedEvent(final Object source,
      final CommentSaveForLater commentSaveForLater) {

    super(source);
    this.commentSaveForLater = commentSaveForLater;
  }
}
