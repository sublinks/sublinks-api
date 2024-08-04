package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonCommentUpdatedEvent extends ApplicationEvent {

  private final LinkPersonComment linkPersonComment;

  public LinkPersonCommentUpdatedEvent(final Object source,
                                       final LinkPersonComment linkPersonComment) {

    super(source);
    this.linkPersonComment = linkPersonComment;
  }
}
