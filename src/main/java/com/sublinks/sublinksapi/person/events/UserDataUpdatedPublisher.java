package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.UserData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserDataUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public UserDataUpdatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final UserData userData) {

    UserDataUpdateEvent userDataUpdateEvent = new UserDataUpdateEvent(this, userData);
    applicationEventPublisher.publishEvent(userDataUpdateEvent);
  }
}
