package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.post.config.PostHistoryConfig;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostHistory;
import com.sublinks.sublinksapi.post.events.PostHistoryCreatedPublisher;
import com.sublinks.sublinksapi.post.repositories.PostHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHistoryService {

  private final PostHistoryConfig postHistoryConfig;
  private final PostHistoryRepository postHistoryRepository;
  private final PostHistoryCreatedPublisher postHistoryCreatedPublisher;

  public PostHistory getPostHistory(final Post post) {

    return PostHistory.builder().post(post).title(post.getTitle()).body(post.getPostBody())
        .url(post.getLinkUrl()).removedState(post.getRemovedState()).isDeleted(post.isDeleted())
        .isLocked(post.isLocked()).isNsfw(post.isNsfw()).build();
  }

  public boolean isDifferent(PostHistory postHistory, Post post) {

    return !postHistory.getTitle().equals(post.getTitle()) && !postHistory.getBody()
        .equals(post.getPostBody()) && !postHistory.getUrl().equals(post.getLinkUrl())
        && !postHistory.getRemovedState().equals(post.getRemovedState())
        && postHistory.getIsDeleted() != post.isDeleted()
        && postHistory.getIsNsfw() != post.isNsfw();
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
}
