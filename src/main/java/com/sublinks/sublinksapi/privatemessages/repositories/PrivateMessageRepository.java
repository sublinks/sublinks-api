package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long>,
    PrivateMessageRepositorySearch {

  @Query("SELECT COUNT(pm) FROM PrivateMessage pm WHERE pm.recipient = :recipient AND pm.isRead = false AND pm.isDeleted = false")
  long countByRecipientAndReadIsFalse(@Param("recipient") Person recipient);

  @Query("SELECT pm FROM PrivateMessage pm WHERE pm.recipient = :recipient AND pm.isRead = false AND pm.isDeleted = false")
  List<PrivateMessage> findByRecipientAndReadIsFalse(@Param("recipient") Person recipient);

  List<PrivateMessage> findAllBySender(Person sender);
}
