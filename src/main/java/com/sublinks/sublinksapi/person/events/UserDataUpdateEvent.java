package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.UserData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataUpdateEvent extends ApplicationEvent {

  private final UserData userData;

  public UserDataUpdateEvent(Object source, UserData userData) {

    super(source);
    this.userData = userData;
  }
}
