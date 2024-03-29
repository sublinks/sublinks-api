package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

  List<UserData> findFirstByPersonAndIpAddress(Person person, String ipAddress);

  Optional<UserData> findFirstByPersonAndIpAddressAndUserAgent(Person person, String ipAddress,
      String userAgent);

  void deleteAllByPerson(Person person);

  List<UserData> findAllByPersonAndLastUsedAtBefore(Person person, Date lastUsedAt);
  List<UserData> findAllByLastUsedAtBefore(Date lastUsedAt);
}
