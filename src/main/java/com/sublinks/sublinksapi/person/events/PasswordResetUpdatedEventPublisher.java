package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.entities.PasswordReset;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetUpdatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PasswordResetUpdatedEventPublisher(
      final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(final PasswordReset passwordReset) {

    PasswordResetUpdatedEvent passwordResetUpdatedEvent = new PasswordResetUpdatedEvent(this,
        passwordReset);
    applicationEventPublisher.publishEvent(passwordResetUpdatedEvent);
  }
}
