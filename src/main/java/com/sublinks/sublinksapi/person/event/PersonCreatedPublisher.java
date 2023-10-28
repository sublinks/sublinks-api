package com.sublinks.sublinksapi.person.event;

import com.sublinks.sublinksapi.person.Person;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PersonCreatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishPersonCreatedEvent(Person person) {
        PersonCreatedEvent personCreatedEvent = new PersonCreatedEvent(this, person);
        applicationEventPublisher.publishEvent(personCreatedEvent);
    }
}
