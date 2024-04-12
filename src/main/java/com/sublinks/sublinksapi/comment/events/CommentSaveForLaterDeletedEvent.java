package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentSaveForLater;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentSaveForLaterDeletedEvent extends ApplicationEvent {

  private final CommentSaveForLater commentSaveForLater;

  public CommentSaveForLaterDeletedEvent(final Object source,
                                         final CommentSaveForLater commentSaveForLater) {

    super(source);
    this.commentSaveForLater = commentSaveForLater;
  }
}
