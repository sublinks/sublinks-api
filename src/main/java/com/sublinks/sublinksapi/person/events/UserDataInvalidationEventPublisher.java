package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserDataInvalidationEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public UserDataInvalidationEventPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final Person person) {

    UserDataInvalidationEvent userDataInvalidationEvent = new UserDataInvalidationEvent(this,
        person);
    applicationEventPublisher.publishEvent(userDataInvalidationEvent);
  }

  public void publish(final PersonMetaData personMetaData) {

    UserDataInvalidationEvent userDataInvalidationEvent = new UserDataInvalidationEvent(this,
        personMetaData);
    applicationEventPublisher.publishEvent(userDataInvalidationEvent);
  }
}
