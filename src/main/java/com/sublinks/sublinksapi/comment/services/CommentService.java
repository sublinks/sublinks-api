package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import java.util.Optional;
import com.sublinks.sublinksapi.person.dto.Person;
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

  /**
   * Generates an ActivityPub ID for a given comment.
   *
   * @param comment The comment for which the ActivityPub ID is being generated.
   * @return A string representing the ActivityPub ID.
   */
  public String generateActivityPubId(final com.sublinks.sublinksapi.comment.dto.Comment comment) {

    String domain = localInstanceContext.instance().getDomain();
    return String.format("%s/comment/%d", domain, comment.getId());
  }

  /**
   * Retrieves the parent comment of a given comment, if it exists.
   *
   * @param comment The comment whose parent is to be retrieved.
   * @return An Optional containing the parent Comment, or empty if no parent
   *         exists.
   */
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

  /**
   * Creates a new comment without a parent and publishes an event upon creation.
   *
   * @param comment The Comment object to be created.
   */
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

  /**
   * Creates a new comment as a reply to a specified parent and publishes an event
   * upon creation.
   *
   * @param comment The Comment object to be created, a reply to the parent.
   * @param parent  The parent Comment of the new comment.
   */
  @Transactional
  public void createComment(final Comment comment, final Comment parent) {

    commentRepository.saveAndFlush(comment);
    comment.setPath(String.format("%s.%d", parent.getPath(), comment.getId()));
    createComment(comment);
  }

  /**
   * Updates a comment without publishing any event.
   *
   * @param comment The Comment object to be updated.
   */
  @Transactional
  public void updateCommentQuietly(final Comment comment) {

    commentRepository.save(comment);
  }

  /**
   * Updates a comment and publishes an event upon update.
   *
   * @param comment The Comment object to be updated.
   */
  @Transactional
  public void updateComment(final Comment comment) {

    commentRepository.save(comment);
    commentUpdatedPublisher.publish(comment);
  }

  /**
   * Removes all comments made by a specific user in a specific community.
   *
   * @param community The community from which comments are to be removed.
   * @param person    The user whose comments are to be removed.
   * @param removed   Boolean flag indicating whether the comments should be
   *                  marked as removed.
   */
  @Transactional
  public void removeAllCommentsFromCommunityAndUser(final Community community, final Person person,
      final boolean removed) {

    commentRepository.allCommentsByCommunityAndPerson(community, person).forEach(comment -> {
      comment.setRemoved(removed);
      commentRepository.save(comment);
    });
  }

  /**
   * Removes all comments made by a specific user.
   *
   * @param person  The user whose comments are to be removed.
   * @param removed Boolean flag indicating whether the comments should be marked
   *                as removed.
   */
  @Transactional
  public void removeAllCommentsFromUser(final Person person, final boolean removed) {

    commentRepository.allCommentsByPerson(person).forEach(comment -> {
      comment.setRemoved(removed);
      commentRepository.save(comment);
    });
  }

}
