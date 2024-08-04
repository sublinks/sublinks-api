package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonDeletedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(Person person, Boolean deleteContent) {

    PersonDeletedEvent personDeletedEvent = new PersonDeletedEvent(this, person, deleteContent);
    applicationEventPublisher.publishEvent(personDeletedEvent);
  }
}
