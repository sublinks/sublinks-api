package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonPost;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonPostUpdatedEvent extends ApplicationEvent {

  private final LinkPersonPost linkPersonPost;

  public LinkPersonPostUpdatedEvent(
      final Object source,
      final LinkPersonPost linkPersonPost
  ) {

    super(source);
    this.linkPersonPost = linkPersonPost;
  }
}
