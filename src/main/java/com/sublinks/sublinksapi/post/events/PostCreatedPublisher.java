package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.Post;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PostCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PostCreatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(Post post) {

    PostCreatedEvent postCreatedEvent = new PostCreatedEvent(this, post);
    applicationEventPublisher.publishEvent(postCreatedEvent);
  }
}
