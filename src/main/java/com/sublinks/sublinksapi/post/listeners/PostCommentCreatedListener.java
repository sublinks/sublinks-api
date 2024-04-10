package com.sublinks.sublinksapi.post.listeners;

import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.post.entities.PostAggregate;
import com.sublinks.sublinksapi.post.repositories.PostAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostCommentCreatedListener implements ApplicationListener<CommentCreatedEvent> {

  private final PostAggregateRepository postAggregateRepository;

  @Override
  @Transactional
  public void onApplicationEvent(final CommentCreatedEvent event) {

    if (!event.getComment().isLocal()) {
      return;
    }

    final PostAggregate postAggregate = event.getComment().getPost().getPostAggregate();
    postAggregate.setCommentCount(postAggregate.getCommentCount() + 1);
    postAggregateRepository.save(postAggregate);
  }
}
