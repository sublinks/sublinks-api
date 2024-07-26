package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.Person;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonDeletedEvent extends ApplicationEvent {

  private final Person person;
  private final Boolean deleteContent;

  public PersonDeletedEvent(final Object source, final Person person, final Boolean deleteContent) {

    super(source);
    this.person = person;
    this.deleteContent = deleteContent;
  }
}
