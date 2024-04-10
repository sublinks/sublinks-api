package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PasswordReset;
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
