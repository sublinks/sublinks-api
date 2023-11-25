package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.person.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long>,
    CommentReplyRepositorySearch {

  long countByRecipientAndIsReadFalse(Person recipient);
}
