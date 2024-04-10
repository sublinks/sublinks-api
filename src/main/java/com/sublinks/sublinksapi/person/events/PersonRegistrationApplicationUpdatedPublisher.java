package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonRegistrationApplicationUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonRegistrationApplicationUpdatedPublisher(
      ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(PersonRegistrationApplication personRegistrationApplication) {

    PersonRegistrationApplicationUpdatedEvent personRegistrationApplicationUpdatedEvent = new PersonRegistrationApplicationUpdatedEvent(
        this, personRegistrationApplication);
    applicationEventPublisher.publishEvent(personRegistrationApplicationUpdatedEvent);
  }
}
