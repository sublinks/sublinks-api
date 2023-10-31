package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.LinkPersonPost;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LinkPersonPostDeletedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public LinkPersonPostDeletedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(LinkPersonPost linkPersonPost) {

        LinkPersonPostDeletedEvent linkPersonPostDeletedEvent = new LinkPersonPostDeletedEvent(this, linkPersonPost);
        applicationEventPublisher.publishEvent(linkPersonPostDeletedEvent);
    }
}
