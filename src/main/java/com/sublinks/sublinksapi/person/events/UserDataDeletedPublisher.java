package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.UserData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserDataDeletedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public UserDataDeletedPublisher(
      final ApplicationEventPublisher applicationEventPublisher
  ) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final UserData userData) {

    UserDataDeletedEvent userDataDeletedEvent = new UserDataDeletedEvent(
        this, userData);
    applicationEventPublisher.publishEvent(userDataDeletedEvent);
  }
}
