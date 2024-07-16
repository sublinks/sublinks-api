package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkPersonPersonDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(LinkPersonPerson linkPersonPerson) {

    final LinkPersonPersonDeletedEvent linkPersonPersonDeletedEvent = new LinkPersonPersonDeletedEvent(
        this, linkPersonPerson);
    applicationEventPublisher.publishEvent(linkPersonPersonDeletedEvent);
  }
}
