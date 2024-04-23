package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHistoryRepository extends JpaRepository<CommentHistory, Long>,
    CommentHistoryRepositoryExtended {

  List<CommentHistory> findByCommentOrderByCreatedAtAsc(Comment post);

  Optional<CommentHistory> findFirstByCommentOrderByCreatedAtDesc(Comment post);

  int deleteAllByComment(Comment comment);
}
