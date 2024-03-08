package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.person.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long>,
    CommentReplyRepositorySearch {

  long countByRecipientAndIsReadFalse(Person recipient);

  @Modifying
  @Transactional
  @Query("DELETE FROM CommentReply cr WHERE cr.comment.id = :commentId")
  void deleteByCommentId(@Param("commentId") Long commentId);
}
