package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long>,
    CommentReplyRepositorySearch {

  long countByRecipientAndIsReadFalse(Person recipient);
}
