package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import com.sublinks.sublinksapi.person.events.PersonRegistrationApplicationCreatedPublisher;
import com.sublinks.sublinksapi.person.events.PersonRegistrationApplicationUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.PersonRegistrationApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonRegistrationApplicationService {

  private final LocalInstanceContext localInstanceContext;
  private final PersonRegistrationApplicationRepository personRegistrationApplicationRepository;
  private final PersonRegistrationApplicationCreatedPublisher personRegistrationApplicationCreatedPublisher;
  private final PersonRegistrationApplicationUpdatedPublisher personRegistrationApplicationUpdatedPublisher;

  public void createPersonRegistrationApplication(
      PersonRegistrationApplication personRegistrationApplication) {

    personRegistrationApplicationRepository.save(personRegistrationApplication);
    personRegistrationApplicationCreatedPublisher.publish(personRegistrationApplication);
  }

  public void updatePersonRegistrationApplication(
      PersonRegistrationApplication personRegistrationApplication) {

    personRegistrationApplicationRepository.save(personRegistrationApplication);
    personRegistrationApplicationUpdatedPublisher.publish(personRegistrationApplication);
  }
}
