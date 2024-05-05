package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserDataUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public UserDataUpdatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PersonMetaData personMetaData) {

    UserDataUpdateEvent userDataUpdateEvent = new UserDataUpdateEvent(this, personMetaData);
    applicationEventPublisher.publishEvent(userDataUpdateEvent);
  }
}
