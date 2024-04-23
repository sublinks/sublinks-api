package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.UserData;
import com.sublinks.sublinksapi.person.events.UserDataCreatedEventPublisher;
import com.sublinks.sublinksapi.person.events.UserDataInvalidationEventPublisher;
import com.sublinks.sublinksapi.person.events.UserDataUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.UserDataRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDataService {

  private final UserDataRepository userDataRepository;
  private final UserDataConfig userDataConfig;
  private final UserDataCreatedEventPublisher userDataCreatedEventPublisher;
  private final UserDataUpdatedPublisher userDataUpdatedPublisher;
  private final UserDataInvalidationEventPublisher userDataInvalidationEventPublisher;


  public void invalidate(UserData userData) {

    userData.setActive(false);
    userDataRepository.save(userData);
    userDataInvalidationEventPublisher.publish(userData);
  }

  public void checkAndAddIpRelation(Person person, String ipAddress, String token,
      @Nullable String userAgent) {

    boolean saveUserIps = userDataConfig.isSaveUserData();
    Optional<UserData> foundData = getActiveUserDataByPersonAndIpAddress(person, token, ipAddress,
        userAgent);

    if (foundData.isPresent()) {

      UserData userData = foundData.get();

      if (saveUserIps && userAgent != null && !userData.getUserAgent().equals(userAgent)) {
        final UserData newUserData = UserData.builder()
            .person(person)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .token(token)
            .active(true)
            .build();

        userDataRepository.save(newUserData);
        userDataCreatedEventPublisher.publish(newUserData);
        return;
      }

      if (saveUserIps && !userData.getIpAddress().equals(ipAddress)) {
        final UserData newUserData = UserData.builder()
            .person(person)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .token(token)
            .active(true)
            .build();

        userDataRepository.save(newUserData);
        userDataCreatedEventPublisher.publish(newUserData);
        return;
      }

      userData.setLastUsedAt(new Date());

      userDataRepository.save(userData);
      userDataUpdatedPublisher.publish(userData);
      return;
    }
    UserData userData = UserData.builder()
        .person(person)
        .ipAddress(saveUserIps ? ipAddress : null)
        .userAgent(saveUserIps ? userAgent : null)
        .token(token)
        .active(true)
        .build();
    UserData createdUserData = userDataRepository.save(userData);
    userDataCreatedEventPublisher.publish(createdUserData);
  }

  public Optional<UserData> getActiveUserDataByPersonAndToken(Person person, String token) {

    return userDataRepository.findFirstByPersonAndTokenAndActiveIsTrue(person, token);
  }

  private Optional<UserData> getActiveUserDataByPersonAndIpAddress(Person person, String token,
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

  public void invalidateUserData(UserData userData) {

    invalidate(userData);
  }

  public void clearUserDataBefore(Person person, Date lastUsedAt) {

    List<UserData> found = userDataRepository.findAllByPersonAndLastUsedAtBefore(person,
        lastUsedAt);
    found.forEach(this::invalidate);
  }

  public void clearUserDataBefore(Date lastUsedAt) {

    List<UserData> found = userDataRepository.findAllByLastUsedAtBefore(lastUsedAt);
    found.forEach(this::invalidate);
  }
}
