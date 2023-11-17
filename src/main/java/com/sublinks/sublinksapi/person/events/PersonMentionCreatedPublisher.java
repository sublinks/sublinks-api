package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonMentionCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PersonMentionCreatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(PersonMention person) {

        PersonMentionCreatedEvent personMentionCreatedEvent = new PersonMentionCreatedEvent(this, person);
        applicationEventPublisher.publishEvent(personMentionCreatedEvent);
    }
}
