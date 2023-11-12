package com.sublinks.sublinksapi.private_messages.repositories;

import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long>, PrivateMessageRepositorySearch {
}
