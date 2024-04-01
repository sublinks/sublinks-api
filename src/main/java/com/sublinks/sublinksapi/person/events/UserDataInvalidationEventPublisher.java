package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
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

  public void publish(final UserData userData) {

    UserDataInvalidationEvent userDataInvalidationEvent = new UserDataInvalidationEvent(this,
        userData);
    applicationEventPublisher.publishEvent(userDataInvalidationEvent);
  }
}
