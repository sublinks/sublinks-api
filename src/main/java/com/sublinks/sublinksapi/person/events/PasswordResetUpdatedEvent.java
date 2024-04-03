package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.PasswordReset;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordResetUpdatedEvent extends ApplicationEvent {

  private final PasswordReset passwordReset;

  public PasswordResetUpdatedEvent(Object source, PasswordReset passwordReset) {

    super(source);
    this.passwordReset = passwordReset;
  }
}
