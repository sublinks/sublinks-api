package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonEmailVerificationCreatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonEmailVerificationCreatedEventPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PersonEmailVerification personEmailVerification) {

    PersonEmailVerificationCreatedEvent personEmailVerificationCreatedEvent = new PersonEmailVerificationCreatedEvent(
        this, personEmailVerification);
    applicationEventPublisher.publishEvent(personEmailVerificationCreatedEvent);
  }
}
