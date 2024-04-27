package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataCreatedEvent extends ApplicationEvent {

  private final PersonMetaData personMetaData;

  public UserDataCreatedEvent(Object source, PersonMetaData personMetaData) {

    super(source);
    this.personMetaData = personMetaData;
  }
}
