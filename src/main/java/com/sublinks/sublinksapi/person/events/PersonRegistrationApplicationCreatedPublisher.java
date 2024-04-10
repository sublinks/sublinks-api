package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonRegistrationApplicationCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonRegistrationApplicationCreatedPublisher(
      ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(PersonRegistrationApplication personRegistrationApplication) {

    PersonRegistrationApplicationCreatedEvent personRegistrationApplicationCreatedEvent = new PersonRegistrationApplicationCreatedEvent(
        this, personRegistrationApplication);
    applicationEventPublisher.publishEvent(personRegistrationApplicationCreatedEvent);
  }
}
