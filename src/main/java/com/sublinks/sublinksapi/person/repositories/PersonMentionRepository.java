package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonMention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonMentionRepository extends JpaRepository<PersonMention, Long>,
    PersonMentionRepositorySearch {

  long countByRecipientAndIsReadFalse(Person recipient);
}
