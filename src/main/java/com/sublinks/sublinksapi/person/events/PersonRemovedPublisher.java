package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.Person;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonRemovedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonRemovedPublisher(ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(Person person) {

    PersonRemovedEvent personRemovedEvent = new PersonRemovedEvent(this, person);
    applicationEventPublisher.publishEvent(personRemovedEvent);
  }
}
