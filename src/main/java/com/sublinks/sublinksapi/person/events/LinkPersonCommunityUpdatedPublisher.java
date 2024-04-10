package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkPersonCommunityUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(LinkPersonCommunity linkPersonCommunity) {

    final LinkPersonCommunityUpdatedEvent linkPersonCommunityUpdatedEvent = new LinkPersonCommunityUpdatedEvent(
        this, linkPersonCommunity);
    applicationEventPublisher.publishEvent(linkPersonCommunityUpdatedEvent);
  }
}
