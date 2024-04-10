package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import com.sublinks.sublinksapi.person.events.PersonEmailVerificationCreatedEventPublisher;
import com.sublinks.sublinksapi.person.events.PersonEmailVerificationUpdatedEventPublisher;
import com.sublinks.sublinksapi.person.repositories.PersonEmailVerificationRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonEmailVerificationService {

  private final PersonEmailVerificationCreatedEventPublisher personEmailVerificationCreatedEventPublisher;
  private final PersonEmailVerificationUpdatedEventPublisher personEmailVerificationUpdatedEventPublisher;
  private final PersonEmailVerificationRepository personEmailVerificationRepository;

  public PersonEmailVerification create(Person person, String ipAddress, String userAgent) {

    PersonEmailVerification personEmailVerification = new PersonEmailVerification();
    personEmailVerification.setPerson(person);
    personEmailVerification.setToken(UUID.randomUUID().toString());
    personEmailVerification.setIpAddress(ipAddress);
    personEmailVerification.setUserAgent(userAgent);
    personEmailVerification.setActive(true);

    personEmailVerificationRepository.save(personEmailVerification);
    personEmailVerificationCreatedEventPublisher.publish(personEmailVerification);

    return personEmailVerification;
  }

  public void update(PersonEmailVerification personEmailVerification) {

    personEmailVerificationRepository.save(personEmailVerification);
    personEmailVerificationUpdatedEventPublisher.publish(personEmailVerification);
  }

  public Optional<PersonEmailVerification> getActiveVerificationByToken(String token) {

    return personEmailVerificationRepository.findOneByTokenAndActiveIsTrue(token);
  }
}
