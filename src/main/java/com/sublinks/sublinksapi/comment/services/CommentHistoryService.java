package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.config.CommentHistoryConfig;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentHistory;
import com.sublinks.sublinksapi.comment.events.CommentHistoryCreatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentHistoryRepository;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentHistoryService {

  private final CommentHistoryConfig commentHistoryConfig;
  private final CommentHistoryRepository commentHistoryRepository;
  private final CommentHistoryCreatedPublisher commentHistoryCreatedPublisher;

  public CommentHistory getCommentHistory(final Comment comment) {

    return CommentHistory.builder().comment(comment).content(comment.getCommentBody())
        .removedState(comment.getRemovedState()).isDeleted(comment.isDeleted()).build();
  }

  public boolean isDifferent(CommentHistory commentHistory, Comment comment) {

    return !commentHistory.getContent().equals(comment.getCommentBody())
        && !commentHistory.getIsDeleted().equals(comment.isDeleted())
        && commentHistory.getRemovedState().equals(comment.getRemovedState());
  }


  @Transactional
  public void createOrAddCommentHistory(Comment comment) {

    if (!commentHistoryConfig.isKeepCommentHistory()) {
      return;
    }

    Optional<CommentHistory> oldHistory = commentHistoryRepository.findFirstByCommentOrderByCreatedAtDesc(
        comment);

    if ((oldHistory.isPresent() && isDifferent(oldHistory.get(), comment))
        || oldHistory.isEmpty()) {
      CommentHistory newPostHistory = getCommentHistory(comment);
      commentHistoryRepository.save(newPostHistory);
      commentHistoryCreatedPublisher.publish(newPostHistory);
    }

  }
}
