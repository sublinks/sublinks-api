package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonEmailVerificationUpdatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonEmailVerificationUpdatedEventPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PersonEmailVerification personEmailVerification) {

    PersonEmailVerificationUpdatedEvent personEmailVerificationUpdatedEvent = new PersonEmailVerificationUpdatedEvent(
        this, personEmailVerification);
    applicationEventPublisher.publishEvent(personEmailVerificationUpdatedEvent);
  }
}
