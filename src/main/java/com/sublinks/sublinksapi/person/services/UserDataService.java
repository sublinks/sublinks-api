package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
import com.sublinks.sublinksapi.person.repositories.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

  public void checkAndAddIpRelation(Person person, String ipAddress) {

    boolean saveUserIps = userDataConfig.isSaveUserIps();
    boolean existsUserDataByPersonAndIpAddress = userDataRepository.existsUserDataByPersonAndIpAddress(
        person, ipAddress);
    if (saveUserIps && !existsUserDataByPersonAndIpAddress) {
      UserData userData = new UserData();
      userData.setPerson(person);
      userData.setIpAddress(ipAddress);
      save(userData);
    }
  }
}
