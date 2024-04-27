package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserDataCreatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public UserDataCreatedEventPublisher(
      final ApplicationEventPublisher applicationEventPublisher
  ) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PersonMetaData personMetaData) {

    UserDataCreatedEvent userDataCreatedEvent = new UserDataCreatedEvent(
        this, personMetaData);
    applicationEventPublisher.publishEvent(userDataCreatedEvent);
  }
}
