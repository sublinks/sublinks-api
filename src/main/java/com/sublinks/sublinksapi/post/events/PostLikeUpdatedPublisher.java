package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final PostLike postLike, final PostLikeUpdatedEvent.Action action) {

    PostLikeUpdatedEvent postLikeUpdatedEvent = new PostLikeUpdatedEvent(this, postLike, action);
    applicationEventPublisher.publishEvent(postLikeUpdatedEvent);
  }
}
