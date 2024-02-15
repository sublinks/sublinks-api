package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
import com.sublinks.sublinksapi.person.repositories.UserDataRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDataService {

  private final UserDataRepository userDataRepository;
  private final UserDataConfig userDataConfig;

  public UserData save(UserData userData) {

    return userDataRepository.save(userData);
  }

  public void delete(UserData userData) {

    userDataRepository.delete(userData);
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
}
