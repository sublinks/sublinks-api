package com.sublinks.sublinksapi.email.repositories;

import com.sublinks.sublinksapi.email.entities.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {

  Optional<Email> findFirstByOrderByLastTryAtAsc();

}
