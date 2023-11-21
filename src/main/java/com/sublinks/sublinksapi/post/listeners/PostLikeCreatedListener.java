package com.sublinks.sublinksapi.post.listeners;

import com.sublinks.sublinksapi.post.dto.PostAggregate;
import com.sublinks.sublinksapi.post.events.PostLikeCreatedEvent;
import com.sublinks.sublinksapi.post.repositories.PostAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostLikeCreatedListener implements ApplicationListener<PostLikeCreatedEvent> {

  private final PostAggregateRepository postAggregateRepository;

  @Override
  @Transactional
  public void onApplicationEvent(PostLikeCreatedEvent event) {

    final PostAggregate postAggregate = event.getPostLike().getPost().getPostAggregate();
    if (event.getPostLike().isUpVote()) {
      postAggregate.setUpVoteCount(postAggregate.getUpVoteCount() + 1);
      postAggregate.setScore(postAggregate.getScore() + 1);
    } else if (event.getPostLike().isDownVote()) {
      postAggregate.setUpVoteCount(postAggregate.getUpVoteCount() - 1);
      postAggregate.setScore(postAggregate.getScore() - 1);
    } else {
      return;
    }
    postAggregateRepository.save(postAggregate);
  }
}
