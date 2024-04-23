package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentSave;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentSaveDeletedEvent extends ApplicationEvent {

  private final CommentSave commentSave;

  public CommentSaveDeletedEvent(final Object source,
      final CommentSave commentSave) {

    super(source);
    this.commentSave = commentSave;
  }
}
