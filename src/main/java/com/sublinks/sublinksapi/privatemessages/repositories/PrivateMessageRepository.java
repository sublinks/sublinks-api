package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long>,
    PrivateMessageRepositorySearch {

  long countByRecipientAndIsReadFalse(Person recipient);

}
