package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(Post post) {

    PostUpdatedEvent postUpdatedEvent = new PostUpdatedEvent(this, post);
    applicationEventPublisher.publishEvent(postUpdatedEvent);
  }
}
