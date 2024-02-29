package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.UserData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataDeletedEvent extends ApplicationEvent {

  private final UserData userData;

  public UserDataDeletedEvent(Object source, UserData userData) {

    super(source);
    this.userData = userData;
  }
}
