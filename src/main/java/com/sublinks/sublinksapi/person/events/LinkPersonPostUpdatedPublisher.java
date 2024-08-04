package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.LinkPersonPost;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LinkPersonPostUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public LinkPersonPostUpdatedPublisher(ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(LinkPersonPost linkPersonPost) {

    LinkPersonPostUpdatedEvent linkPersonPostUpdatedEvent = new LinkPersonPostUpdatedEvent(this,
        linkPersonPost);
    applicationEventPublisher.publishEvent(linkPersonPostUpdatedEvent);
  }
}
