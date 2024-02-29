package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.events.PostLikeCreatedPublisher;
import com.sublinks.sublinksapi.post.events.PostLikeUpdatedEvent;
import com.sublinks.sublinksapi.post.events.PostLikeUpdatedPublisher;
import com.sublinks.sublinksapi.post.models.PostLikeSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostLikeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;
  private final PostLikeCreatedPublisher postLikeCreatedPublisher;
  private final PostLikeUpdatedPublisher postLikeUpdatedPublisher;

  @Transactional
  public void updateOrCreatePostLikeLike(final Post post, final Person person) {

    updateOrCreatePostLike(post, person, 1);
  }

  @Transactional
  public void updateOrCreatePostLikeDislike(final Post post, final Person person) {

    updateOrCreatePostLike(post, person, -1);
  }

  @Transactional
  public void updateOrCreatePostLikeNeutral(final Post post, final Person person) {

    updateOrCreatePostLike(post, person, 0);
  }

  public Optional<PostLike> getPostLike(final Post post, final Person person) {

    return postLikeRepository.getPostLikesByPostAndPerson(post, person);
  }

  private void updateOrCreatePostLike(final Post post, final Person person, final int score) {

    final Optional<PostLike> postLike = getPostLike(post, person);
    if (postLike.isEmpty()) {
      createPostLike(post, person, score);
    } else {
      updatePostLike(postLike.get(), score);
    }
  }

  private void createPostLike(final Post post, final Person person, final int score) {

    final PostLike postLike = PostLike.builder().post(post).person(person).isUpVote(score == 1)
        .isDownVote(score == -1).score(score).build();
    postLikeRepository.save(postLike);
    postLikeCreatedPublisher.publish(postLike);
  }

  private void updatePostLike(final PostLike postLike, final int score) {

    PostLikeUpdatedEvent.Action action = PostLikeUpdatedEvent.Action.NO_CHANGE;
    if (postLike.isUpVote()) {
      if (score == -1) {
        action = PostLikeUpdatedEvent.Action.FROM_UP_TO_DOWN;
      } else if (score == 0) {
        action = PostLikeUpdatedEvent.Action.FROM_UP_TO_NEUTRAL;
      }
    } else if (postLike.isDownVote()) {
      if (score == 1) {
        action = PostLikeUpdatedEvent.Action.FROM_DOWN_TO_UP;
      } else if (score == 0) {
        action = PostLikeUpdatedEvent.Action.FROM_DOWN_TO_NEUTRAL;
      }
    } else {
      if (score == 1) {
        action = PostLikeUpdatedEvent.Action.FROM_NEUTRAL_TO_UP;
      } else if (score == -1) {
        action = PostLikeUpdatedEvent.Action.FROM_NEUTRAL_TO_DOWN;
      }
    }

    postLike.setUpVote(score == 1);
    postLike.setDownVote(score == -1);
    postLike.setScore(score);
    postLikeRepository.save(postLike);
    postLikeUpdatedPublisher.publish(postLike, action);
  }

  public List<PostLike> getPostLikes(final Post post, final int page, final int perPage) {

    return postLikeRepository.allPostLikesBySearchCriteria(
        PostLikeSearchCriteria.builder().postId(post.getId()).perPage(perPage).page(page).build());
  }
}
