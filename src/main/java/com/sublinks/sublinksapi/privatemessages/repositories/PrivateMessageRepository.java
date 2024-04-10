package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long>,
    PrivateMessageRepositorySearch {

  long countByRecipientAndIsReadFalse(Person recipient);

}
