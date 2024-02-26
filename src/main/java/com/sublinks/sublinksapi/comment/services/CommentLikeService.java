package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.comment.events.CommentLikeCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentLikeUpdatedEvent;
import com.sublinks.sublinksapi.comment.events.CommentLikeUpdatedPublisher;
import com.sublinks.sublinksapi.comment.models.CommentLikeSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentLikeRepository;
import com.sublinks.sublinksapi.person.dto.Person;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentLikeService {

  private final CommentLikeRepository commentLikeRepository;
  private final CommentLikeCreatedPublisher commentLikeCreatedPublisher;
  private final CommentLikeUpdatedPublisher commentLikeUpdatedPublisher;

  /**
   * Retrieves the voting score of a specific person for a given comment.
   *
   * @param person  The person whose vote is being queried.
   * @param comment The comment for which the vote is being queried.
   * @return An integer representing the vote: 1 for an like, -1 for a dislike, and 0 if neutral.
   */
  @NonNull
  public int getPersonCommentVote(final Person person, final Comment comment) {

    Optional<CommentLike> commentLike = commentLikeRepository.getCommentLikeByPersonAndComment(
        person, comment);
    return commentLike.map(CommentLike::getScore).orElse(0);
  }

  /**
   * Creates or updates a like for a given comment by a specific person.
   *
   * @param comment The comment to be liked.
   * @param person  The person who is liking the comment.
   */
  @NonNull
  public void updateOrCreateCommentLikeLike(final Comment comment, final Person person) {

    updateOrCreateCommentLike(comment, person, 1);
  }

  /**
   * Creates or updates a dislike for a given comment by a specific person.
   *
   * @param comment The comment to be disliked.
   * @param person  The person who is disliking the comment.
   */
  @NonNull
  public void updateOrCreateCommentLikeDislike(final Comment comment, final Person person) {

    updateOrCreateCommentLike(comment, person, -1);
  }

  /**
   * Creates or updates a neutral vote for a given comment by a specific person.
   *
   * @param comment The comment to have a neutral vote.
   * @param person  The person who is setting the neutral vote.
   */
  @NonNull
  public void updateOrCreateCommentLikeNeutral(final Comment comment, final Person person) {

    updateOrCreateCommentLike(comment, person, 0);
  }

  /**
   * Retrieves a {@link CommentLike} instance for a specific comment and person, if it exists.
   *
   * @param comment The comment in question.
   * @param person  The person in question.
   * @return An Optional containing the CommentLike if it exists, or empty otherwise.
   */
  @NonNull
  public Optional<CommentLike> getCommentLike(final Comment comment, final Person person) {

    return commentLikeRepository.getCommentLikeByPersonAndComment(person, comment);
  }

  @NonNull
  public List<CommentLike> getCommentLikes(final Comment comment, final int page,
      final int perPage) {

    return commentLikeRepository.allCommentLikesBySearchCriteria(
        CommentLikeSearchCriteria.builder().commentId(comment.getId()).perPage(perPage).page(page)
            .build());

  }

  @NonNull
  private void updateOrCreateCommentLike(final Comment comment, final Person person,
      final int score) {

    final Optional<CommentLike> commentLike = getCommentLike(comment, person);
    if (commentLike.isEmpty()) {
      createCommentLike(comment, person, score);
    } else {
      updateCommentLike(commentLike.get(), score);
    }
  }

  private void createCommentLike(final Comment comment, final Person person, final int score) {

    final CommentLike commentLike = CommentLike.builder().comment(comment).post(comment.getPost())
        .person(person).isUpVote(score == 1).isDownVote(score == -1).score(score).build();
    commentLikeRepository.save(commentLike);
    commentLikeCreatedPublisher.publish(commentLike);
  }

  private void updateCommentLike(final CommentLike commentLike, final int score) {

    CommentLikeUpdatedEvent.Action action = CommentLikeUpdatedEvent.Action.NO_CHANGE;
    if (commentLike.isUpVote()) {
      if (score == -1) {
        action = CommentLikeUpdatedEvent.Action.FROM_UP_TO_DOWN;
      } else if (score == 0) {
        action = CommentLikeUpdatedEvent.Action.FROM_UP_TO_NEUTRAL;
      }
    } else if (commentLike.isDownVote()) {
      if (score == 1) {
        action = CommentLikeUpdatedEvent.Action.FROM_DOWN_TO_UP;
      } else if (score == 0) {
        action = CommentLikeUpdatedEvent.Action.FROM_DOWN_TO_NEUTRAL;
      }
    } else {
      if (score == 1) {
        action = CommentLikeUpdatedEvent.Action.FROM_NEUTRAL_TO_UP;
      } else if (score == -1) {
        action = CommentLikeUpdatedEvent.Action.FROM_NEUTRAL_TO_DOWN;
      }
    }

    commentLike.setUpVote(score == 1);
    commentLike.setDownVote(score == -1);
    commentLike.setScore(score);
    commentLikeRepository.save(commentLike);
    commentLikeUpdatedPublisher.publish(commentLike, action);
  }
}
