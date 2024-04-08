package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessageReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrivateMessageReportRepository extends JpaRepository<PrivateMessageReport, Long>,
    PrivateMessageReportSearchRepository {

  @Modifying
  @Query("UPDATE PrivateMessageReport pmr SET pmr.resolved = true, pmr.resolver = :resolver WHERE pmr.resolved = false AND pmr.privateMessage.sender = :sender")
  void resolveAllReportsByPerson(@Param("sender") Person sender,
      @Param("resolver") Person resolver);
}
