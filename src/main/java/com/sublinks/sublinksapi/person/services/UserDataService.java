package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
import com.sublinks.sublinksapi.person.events.UserDataCreatedPublisher;
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
  private final UserDataCreatedPublisher userDataCreatedPublisher;
  private final UserDataUpdatedPublisher userDataUpdatedPublisher;
  private final UserDataInvalidationEventPublisher userDataInvalidationEventPublisher;


  public void invalidate(UserData userData) {

    userData.setActive(false);
    userDataRepository.save(userData);
    userDataInvalidationEventPublisher.publish(userData);
  }

  public void checkAndAddIpRelation(Person person, String ipAddress, String token,
      @Nullable String userAgent) {

    boolean saveUserIps = userDataConfig.isSaveUserIps();
    Optional<UserData> foundData = getActiveUserDataByPersonAndToken(person, token);

    if (saveUserIps) {
      if (foundData.isPresent()) {

        UserData userData = foundData.get();

        userData.setLastUsedAt(new Date());

        if (userAgent != null && !userData.getUserAgent().equals(userAgent)) {
          userData.setUserAgent(userAgent);
        }

        if (!userData.getIpAddress().equals(ipAddress)) {
          userData.setIpAddress(ipAddress);
        }

        userDataRepository.save(userData);
        userDataUpdatedPublisher.publish(userData);
        return;
      }
      if (foundData.isEmpty()) {
        UserData userData = UserData.builder()
            .person(person)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .token(token)
            .active(true)
            .lastUsedAt(new Date())
            .build();
        UserData createdUserData = userDataRepository.save(userData);
        userDataCreatedPublisher.publish(createdUserData);
      }
    }
  }

  public Optional<UserData> getActiveUserDataByPersonAndToken(Person person, String token) {

    return userDataRepository.findFirstByPersonAndTokenAndActiveIsTrue(person, token);
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
