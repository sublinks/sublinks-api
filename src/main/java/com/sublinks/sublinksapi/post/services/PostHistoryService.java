package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.config.PostHistoryConfig;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostHistory;
import com.sublinks.sublinksapi.post.events.PostHistoryCreatedPublisher;
import com.sublinks.sublinksapi.post.repositories.PostHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHistoryService {

  private final PostHistoryConfig postHistoryConfig;
  private final PostHistoryRepository postHistoryRepository;
  private final PostHistoryCreatedPublisher postHistoryCreatedPublisher;

  public PostHistory getPostHistory(final Post post) {

    return PostHistory.builder()
        .post(post)
        .title(post.getTitle())
        .body(post.getPostBody())
        .url(post.getLinkUrl())
        .removedState(post.getRemovedState())
        .isDeleted(post.isDeleted())
        .isLocked(post.isLocked())
        .isNsfw(post.isNsfw())
        .build();
  }

  public boolean isDifferent(PostHistory postHistory, Post post) {

    final PostHistory postHistoryNew = getPostHistory(post);

    return postHistory.getIsNsfw() != postHistoryNew.getIsNsfw()
        || postHistory.getIsLocked() != postHistoryNew.getIsLocked()
        || postHistory.getIsDeleted() != postHistoryNew.getIsDeleted()
        || postHistory.getRemovedState() != postHistoryNew.getRemovedState() || !Objects.equals(
        postHistory.getUrl(), postHistoryNew.getUrl()) || !Objects.equals(postHistory.getTitle(),
        postHistoryNew.getTitle()) || !Objects.equals(postHistory.getBody(),
        postHistoryNew.getBody());
  }


  @Transactional
  public void createOrAddPostHistory(Post post) {

    if (!postHistoryConfig.isKeepPostHistory()) {
      return;
    }

    Optional<PostHistory> oldHistory = postHistoryRepository.findFirstByPostOrderByCreatedAtDesc(
        post);

    if ((oldHistory.isPresent() && isDifferent(oldHistory.get(), post)) || oldHistory.isEmpty()) {
      PostHistory newPostHistory = getPostHistory(post);
      postHistoryRepository.save(newPostHistory);
      postHistoryCreatedPublisher.publish(newPostHistory);
    }

  }

  @Transactional
  public int deleteAllByPost(Post post) {

    return postHistoryRepository.deleteAllByPost(post);
  }

  @Transactional
  public int deleteAllByCreator(Person person) {
    return postHistoryRepository.deleteAllByCreator(person);
  }
}
