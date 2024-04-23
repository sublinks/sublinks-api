package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PersonMentionRepository extends JpaRepository<PersonMention, Long>,
    PersonMentionRepositorySearch {

  @Query("SELECT COUNT(pm) FROM PersonMention pm WHERE pm.recipient = :recipient AND pm.isRead = false AND pm.comment.isDeleted = false AND pm.comment.removedState = 'NOT_REMOVED'")
  long countByRecipientAndIsReadIsFalse(@Param("recipient") Person recipient);

  @Query("SELECT pm FROM PersonMention pm WHERE pm.recipient = :recipient AND pm.isRead = false AND pm.comment.isDeleted = false AND pm.comment.removedState = 'NOT_REMOVED'")
  List<PersonMention> findAllByRecipientAndReadIsFalse(@Param("recipient") Person recipient);
}
