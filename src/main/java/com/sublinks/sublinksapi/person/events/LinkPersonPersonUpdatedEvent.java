package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonPersonUpdatedEvent extends ApplicationEvent {

  private final LinkPersonPerson linkPersonPerson;

  public LinkPersonPersonUpdatedEvent(final Object source,
                                      final LinkPersonPerson linkPersonPerson) {

    super(source);
    this.linkPersonPerson = linkPersonPerson;
  }
}
