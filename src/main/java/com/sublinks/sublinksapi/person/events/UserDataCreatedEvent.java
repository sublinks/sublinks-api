package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.UserData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataCreatedEvent extends ApplicationEvent {

  private final UserData userData;

  public UserDataCreatedEvent(Object source, UserData userData) {

    super(source);
    this.userData = userData;
  }
}
