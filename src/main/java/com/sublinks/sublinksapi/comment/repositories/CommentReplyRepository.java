package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long>,
    CommentReplyRepositorySearch {

  @Query("SELECT COUNT(cr) FROM CommentReply cr WHERE cr.recipient = :recipient AND cr.isRead = false AND cr.comment.isDeleted = false AND cr.comment.removedState = 'NOT_REMOVED'")
  long countByRecipientAndReadIsFalse(@Param("recipient") Person recipient);

  @Query("SELECT cr FROM CommentReply cr WHERE cr.recipient = :recipient AND cr.isRead = false AND cr.comment.isDeleted = false AND cr.comment.removedState = 'NOT_REMOVED'")
  List<CommentReply> findAllByRecipientAndReadIsFalse(@Param("recipient") Person recipient);
}
