package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.dto.UserData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserDataCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public UserDataCreatedPublisher(
      final ApplicationEventPublisher applicationEventPublisher
  ) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final UserData userData) {

    UserDataCreatedEvent userDataCreatedEvent = new UserDataCreatedEvent(
        this, userData);
    applicationEventPublisher.publishEvent(userDataCreatedEvent);
  }
}
