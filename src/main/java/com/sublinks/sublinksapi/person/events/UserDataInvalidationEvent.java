package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.UserData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataInvalidationEvent extends ApplicationEvent {

  private final Person personPurged;
  private final UserData userData;

  public UserDataInvalidationEvent(Object source, Person personPurged) {

    super(source);
    this.personPurged = personPurged;
    this.userData = null;
  }

  public UserDataInvalidationEvent(Object source, UserData userData) {

    super(source);
    this.personPurged = null;
    this.userData = userData;
  }
}
