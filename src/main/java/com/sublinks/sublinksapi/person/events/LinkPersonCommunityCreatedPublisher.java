package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LinkPersonCommunityCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public LinkPersonCommunityCreatedPublisher(
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(final LinkPersonCommunity linkPersonCommunity) {

        LinkPersonCommunityCreatedEvent linkPersonCommunityCreatedEvent = new LinkPersonCommunityCreatedEvent(this, linkPersonCommunity);
        applicationEventPublisher.publishEvent(linkPersonCommunityCreatedEvent);
    }
}
