package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonRegistrationApplicationUpdatedEvent extends ApplicationEvent {

  private final PersonRegistrationApplication personRegistrationApplication;

  public PersonRegistrationApplicationUpdatedEvent(final Object source,
      final PersonRegistrationApplication personRegistrationApplication) {

    super(source);
    this.personRegistrationApplication = personRegistrationApplication;
  }
}
