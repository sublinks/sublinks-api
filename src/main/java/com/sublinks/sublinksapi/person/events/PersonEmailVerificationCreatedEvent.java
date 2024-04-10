package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonEmailVerificationCreatedEvent extends ApplicationEvent {

  private final PersonEmailVerification personEmailVerification;

  public PersonEmailVerificationCreatedEvent(Object source,
      PersonEmailVerification personEmailVerification) {

    super(source);
    this.personEmailVerification = personEmailVerification;
  }
}
