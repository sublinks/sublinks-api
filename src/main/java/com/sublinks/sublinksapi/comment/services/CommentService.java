package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentAggregateRepository commentAggregateRepository;
  private final CommentCreatedPublisher commentCreatedPublisher;
  private final CommentUpdatedPublisher commentUpdatedPublisher;
  private final LocalInstanceContext localInstanceContext;

  public String generateActivityPubId(final com.sublinks.sublinksapi.comment.dto.Comment comment) {

    String domain = localInstanceContext.instance().getDomain();
    return String.format("%s/comment/%d", domain, comment.getId());
  }

  public Optional<Comment> getParentComment(final Comment comment) {

    if (comment.getPath() == null) {
      return Optional.empty();
    }

    String[] path = comment.getPath().split("\\.");
    if (path.length == 1) {
      return Optional.empty();
    }

    return commentRepository.findById(Long.parseLong(path[path.length - 2]));

  }


  @Transactional
  public void createComment(final Comment comment) {

    if (comment.getPath() == null || comment.getPath().isBlank()) {
      commentRepository.saveAndFlush(comment);
      comment.setPath(String.format("0.%d", comment.getId()));
    }
    comment.setActivityPubId(generateActivityPubId(comment));
    commentRepository.save(comment);

    CommentAggregate commentAggregate = CommentAggregate.builder().comment(comment).build();
    commentAggregateRepository.save(commentAggregate);
    comment.setCommentAggregate(commentAggregate);
    commentRepository.save(comment);

    commentCreatedPublisher.publish(comment);
  }

  @Transactional
  public void createComment(final Comment comment, final Comment parent) {

    commentRepository.saveAndFlush(comment);
    comment.setPath(String.format("%s.%d", parent.getPath(), comment.getId()));
    createComment(comment);
  }

  @Transactional
  public void updateCommentQuietly(final Comment comment) {

    commentRepository.save(comment);
  }

  @Transactional
  public void updateComment(final Comment comment) {

    commentRepository.save(comment);
    commentUpdatedPublisher.publish(comment);
  }
}
