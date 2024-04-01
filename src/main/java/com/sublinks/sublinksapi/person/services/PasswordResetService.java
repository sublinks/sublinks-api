package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.dto.PasswordReset;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.events.PasswordResetCreatedEventPublisher;
import com.sublinks.sublinksapi.person.events.PasswordResetUpdatedEventPublisher;
import com.sublinks.sublinksapi.person.repositories.PasswordResetRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetService {

  private final PasswordResetRepository passwordResetRepository;
  private final PasswordResetCreatedEventPublisher passwordResetCreatedEventPublisher;
  private final PasswordResetUpdatedEventPublisher passwordResetUpdatedEventPublisher;

  public Optional<PasswordReset> findFirstByToken(String token) {

    return passwordResetRepository.findFirstByToken(token);
  }

  public String generateToken() {

    return UUID.randomUUID().toString();
  }

  public PasswordReset createPasswordReset(Person person) {

    PasswordReset passwordReset = new PasswordReset();
    passwordReset.setPerson(person);
    passwordReset.setUsed(false);
    passwordReset.setCreatedAt(new Date());
    passwordReset.setToken(generateToken());

    passwordResetRepository.save(passwordReset);
    passwordResetCreatedEventPublisher.publish(passwordReset);

    return passwordReset;
  }

  public PasswordReset updatePasswordReset(PasswordReset passwordReset) {

    passwordReset.setUsed(true);

    passwordResetRepository.save(passwordReset);
    passwordResetUpdatedEventPublisher.publish(passwordReset);

    return passwordReset;
  }

  public boolean isRateLimited(Person person) {

    Date createdAt = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
    List<PasswordReset> passwordResets = passwordResetRepository.findAllByPersonAndUsedIsFalseAndCreatedAtAfter(
        person, createdAt);

    return passwordResets.size() > 3;
  }
}
