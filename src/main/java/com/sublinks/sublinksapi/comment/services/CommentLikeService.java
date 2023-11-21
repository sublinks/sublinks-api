package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.comment.events.CommentLikeCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentLikeUpdatedEvent;
import com.sublinks.sublinksapi.comment.events.CommentLikeUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentLikeRepository;
import com.sublinks.sublinksapi.person.dto.Person;
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

  @NonNull
  public int getPersonCommentVote(final Person person, final Comment comment) {

    Optional<CommentLike> commentLike = commentLikeRepository.getCommentLikeByPersonAndComment(
        person, comment);
    return commentLike.map(CommentLike::getScore).orElse(0);
  }

  @NonNull
  public void updateOrCreateCommentLikeLike(final Comment comment, final Person person) {

    updateOrCreateCommentLike(comment, person, 1);
  }

  @NonNull
  public void updateOrCreateCommentLikeDislike(final Comment comment, final Person person) {

    updateOrCreateCommentLike(comment, person, -1);
  }

  @NonNull
  public void updateOrCreateCommentLikeNeutral(final Comment comment, final Person person) {

    updateOrCreateCommentLike(comment, person, 0);
  }

  @NonNull
  public Optional<CommentLike> getCommentLike(final Comment comment, final Person person) {

    return commentLikeRepository.getCommentLikeByPersonAndComment(person, comment);
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

    final CommentLike commentLike = CommentLike.builder()
        .comment(comment)
        .post(comment.getPost())
        .person(person)
        .isUpVote(score == 1)
        .isDownVote(score == -1)
        .score(score)
        .build();
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
