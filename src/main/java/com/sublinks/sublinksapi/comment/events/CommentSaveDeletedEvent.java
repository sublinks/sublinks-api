package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentSaveDeletedEvent extends ApplicationEvent {

  private final LinkPersonComment linkPersonComment;

  public CommentSaveDeletedEvent(final Object source,
      final LinkPersonComment linkPersonComment) {

    super(source);
    this.linkPersonComment = linkPersonComment;
  }
}
