package com.sublinks.sublinksapi.person.event;

import com.sublinks.sublinksapi.person.LinkPersonPost;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LinkPersonPostCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public LinkPersonPostCreatedPublisher(
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(final LinkPersonPost linkPersonPost) {

        LinkPersonPostCreatedEvent linkPersonPostCreatedEvent = new LinkPersonPostCreatedEvent(this, linkPersonPost);
        applicationEventPublisher.publishEvent(linkPersonPostCreatedEvent);
    }
}
