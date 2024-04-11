package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonCommunityCreatedEvent extends ApplicationEvent {

  private final LinkPersonCommunity linkPersonCommunity;

  public LinkPersonCommunityCreatedEvent(
      final Object source,
      final LinkPersonCommunity linkPersonCommunity
  ) {

    super(source);
    this.linkPersonCommunity = linkPersonCommunity;
  }
}
