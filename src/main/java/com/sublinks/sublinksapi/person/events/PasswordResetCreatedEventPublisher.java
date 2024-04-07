package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.PasswordReset;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetCreatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PasswordResetCreatedEventPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PasswordReset passwordReset) {

    PasswordResetCreatedEvent passwordResetCreatedEvent = new PasswordResetCreatedEvent(this,
        passwordReset);
    applicationEventPublisher.publishEvent(passwordResetCreatedEvent);
  }
}
