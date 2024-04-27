package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserDataUpdateEvent extends ApplicationEvent {

  private final PersonMetaData personMetaData;

  public UserDataUpdateEvent(Object source, PersonMetaData personMetaData) {

    super(source);
    this.personMetaData = personMetaData;
  }
}
