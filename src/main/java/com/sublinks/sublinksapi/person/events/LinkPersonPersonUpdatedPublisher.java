package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkPersonPersonUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(LinkPersonPerson linkPersonPerson) {

    final LinkPersonPersonUpdatedEvent linkPersonPersonUpdatedEvent = new LinkPersonPersonUpdatedEvent(
        this, linkPersonPerson);
    applicationEventPublisher.publishEvent(linkPersonPersonUpdatedEvent);
  }
}
