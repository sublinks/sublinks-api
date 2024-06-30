package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import com.sublinks.sublinksapi.person.events.UserDataCreatedEventPublisher;
import com.sublinks.sublinksapi.person.events.UserDataInvalidationEventPublisher;
import com.sublinks.sublinksapi.person.events.UserDataUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.UserDataRepository;
import jakarta.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserDataService {

  private final UserDataRepository userDataRepository;
  private final UserDataConfig userDataConfig;
  private final UserDataCreatedEventPublisher userDataCreatedEventPublisher;
  private final UserDataUpdatedPublisher userDataUpdatedPublisher;
  private final UserDataInvalidationEventPublisher userDataInvalidationEventPublisher;


  public void invalidate(PersonMetaData personMetaData) {

    personMetaData.setActive(false);
    userDataRepository.save(personMetaData);
    userDataInvalidationEventPublisher.publish(personMetaData);
  }

  public void checkAndAddIpRelation(Person person, String ipAddress, String token,
      @Nullable String userAgent) {

    boolean saveUserIps = userDataConfig.isSaveUserData();
    Optional<PersonMetaData> foundData = getActiveUserDataByPersonAndIpAddress(person, token,
        ipAddress, userAgent).or(() -> getActiveUserDataByPersonAndToken(person, token));

    if (foundData.isPresent()) {

      PersonMetaData personMetaData = foundData.get();

      if (saveUserIps && userAgent != null && !personMetaData.getUserAgent()
          .equals(userAgent)) {
        final PersonMetaData newPersonMetaData = PersonMetaData.builder()
            .person(person)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .token(token)
            .active(true)
            .build();

        userDataRepository.saveAndFlush(newPersonMetaData);
        userDataCreatedEventPublisher.publish(newPersonMetaData);
        return;
      }

      if (saveUserIps && !personMetaData.getIpAddress()
          .equals(ipAddress)) {
        final PersonMetaData newPersonMetaData = PersonMetaData.builder()
            .person(person)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .token(token)
            .active(true)
            .build();

        userDataRepository.saveAndFlush(newPersonMetaData);
        userDataCreatedEventPublisher.publish(newPersonMetaData);
        return;
      }

      personMetaData.setLastUsedAt(new Date());

      userDataRepository.saveAndFlush(personMetaData);
      userDataUpdatedPublisher.publish(personMetaData);
      return;
    }
    PersonMetaData personMetaData = PersonMetaData.builder()
        .person(person)
        .ipAddress(saveUserIps ? ipAddress : null)
        .userAgent(saveUserIps ? userAgent : null)
        .token(token)
        .active(true)
        .build();
    PersonMetaData createdPersonMetaData = userDataRepository.saveAndFlush(personMetaData);
    userDataCreatedEventPublisher.publish(createdPersonMetaData);
  }

  public Optional<PersonMetaData> getActiveUserDataByPersonAndToken(Person person, String token) {

    return userDataRepository.findFirstByPersonAndTokenAndActiveIsTrue(person, token);
  }

  private Optional<PersonMetaData> getActiveUserDataByPersonAndIpAddress(Person person,
      String token,
      String ipAddress, String userAgent) {

    if (userDataConfig.isSaveUserData()) {
      return userDataRepository.findFirstByPersonAndTokenAndActiveIsTrue(person, ipAddress);
    }

    return userDataRepository.findFirstByPersonAndTokenAndIpAddressAndUserAgentAndActiveIsTrue(
        person, token, ipAddress, userAgent);
  }

  @Transactional
  public void invalidateAllUserData(Person person) {

    userDataRepository.updateAllByPersonSetActiveToFalse(person);
    userDataInvalidationEventPublisher.publish(person);
  }

  public void invalidateUserData(PersonMetaData personMetaData) {

    invalidate(personMetaData);
  }

  public void clearUserDataBefore(Person person, Date lastUsedAt) {

    List<PersonMetaData> found = userDataRepository.findAllByPersonAndLastUsedAtBefore(person,
        lastUsedAt);
    found.forEach(this::invalidate);
  }

  public void clearUserDataBefore(Date lastUsedAt) {

    List<PersonMetaData> found = userDataRepository.findAllByLastUsedAtBefore(lastUsedAt);
    found.forEach(this::invalidate);
  }
}
