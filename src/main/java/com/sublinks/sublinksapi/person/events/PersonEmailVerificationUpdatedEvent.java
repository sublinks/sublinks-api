package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonEmailVerificationUpdatedEvent extends ApplicationEvent {

  private final PersonEmailVerification personEmailVerification;

  public PersonEmailVerificationUpdatedEvent(Object source,
      PersonEmailVerification personEmailVerification) {

    super(source);
    this.personEmailVerification = personEmailVerification;
  }
}
