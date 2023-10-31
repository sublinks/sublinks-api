package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.Person;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonUpdatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PersonUpdatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(Person person) {

        PersonUpdatedEvent personUpdatedEvent = new PersonUpdatedEvent(this, person);
        applicationEventPublisher.publishEvent(personUpdatedEvent);
    }
}
