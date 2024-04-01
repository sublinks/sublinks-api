package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.PasswordReset;
import com.sublinks.sublinksapi.person.dto.UserData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordResetCreatedEvent extends ApplicationEvent {

  private final PasswordReset passwordReset;

  public PasswordResetCreatedEvent(Object source, PasswordReset passwordReset) {

    super(source);
    this.passwordReset = passwordReset;
  }
}
