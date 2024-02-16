package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
import com.sublinks.sublinksapi.person.events.UserDataCreatedPublisher;
import com.sublinks.sublinksapi.person.events.UserDataDeletedPublisher;
import com.sublinks.sublinksapi.person.repositories.UserDataRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDataService {

  private final UserDataRepository userDataRepository;
  private final UserDataConfig userDataConfig;
  private final UserDataCreatedPublisher userDataCreatedPublisher;
  private final UserDataDeletedPublisher userDataDeletedPublisher;

  public void save(UserData userData) {

    UserData createdUserData = userDataRepository.save(userData);
    userDataCreatedPublisher.publish(createdUserData);
  }

  public void delete(UserData userData) {

    userDataRepository.delete(userData);
    userDataDeletedPublisher.publish(userData);
  }

  public void checkAndAddIpRelation(Person person, String ipAddress, @Nullable String userAgent) {

    boolean saveUserIps = userDataConfig.isSaveUserIps();
    Optional<UserData> foundData = userDataRepository.findFirstByPersonAndIpAddressAndUserAgent(
        person, ipAddress, userAgent);
    if (saveUserIps) {
      foundData.ifPresent(userData -> {
        userData.setLastUsedAt(new Date());
        save(userData);
      });
      if (foundData.isEmpty()) {
        UserData userData = UserData.builder().person(person).ipAddress(ipAddress)
            .userAgent(userAgent).build();
        save(userData);
      }
    }
  }

  public void clearUserData(Person person) {

    userDataRepository.deleteAllByPerson(person);
  }

  public void clearUserDataBefore(Person person, Date lastUsedAt) {

    List<UserData> found = userDataRepository.findAllByPersonAndLastUsedAtBefore(person,
        lastUsedAt);
    found.forEach(this::delete);
  }

  public void clearUserDataBefore(Date lastUsedAt) {

    List<UserData> found = userDataRepository.findAllByLastUsedAtBefore(lastUsedAt);
    found.forEach(this::delete);
  }
}
