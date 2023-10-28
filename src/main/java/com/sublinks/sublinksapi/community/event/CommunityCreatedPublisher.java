package com.sublinks.sublinksapi.community.event;

import com.sublinks.sublinksapi.community.Community;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CommunityCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CommunityCreatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCommunityCreatedEvent(Community community) {
        CommunityCreatedEvent communityCreatedEvent = new CommunityCreatedEvent(this, community);
        applicationEventPublisher.publishEvent(communityCreatedEvent);
    }
}
