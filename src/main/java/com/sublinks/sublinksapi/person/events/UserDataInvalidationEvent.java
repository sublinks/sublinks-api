package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataInvalidationEvent extends ApplicationEvent {

  private final Person personPurged;
  private final PersonMetaData personMetaData;

  public UserDataInvalidationEvent(Object source, Person personPurged) {

    super(source);
    this.personPurged = personPurged;
    this.personMetaData = null;
  }

  public UserDataInvalidationEvent(Object source, PersonMetaData personMetaData) {

    super(source);
    this.personPurged = null;
    this.personMetaData = personMetaData;
  }
}
