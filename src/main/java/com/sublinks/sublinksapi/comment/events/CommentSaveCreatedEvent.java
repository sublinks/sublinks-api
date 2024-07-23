package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentSaveCreatedEvent extends ApplicationEvent {

  private final LinkPersonComment linkPersonComment;

  public CommentSaveCreatedEvent(final Object source, final LinkPersonComment linkPersonComment) {

    super(source);
    this.linkPersonComment = linkPersonComment;
  }
}
