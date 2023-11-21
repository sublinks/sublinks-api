package com.sublinks.sublinksapi.community.events;

import com.sublinks.sublinksapi.community.dto.Community;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CommunityCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public CommunityCreatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final Community community) {

    final CommunityCreatedEvent communityCreatedEvent = new CommunityCreatedEvent(this, community);
    applicationEventPublisher.publishEvent(communityCreatedEvent);
  }
}
