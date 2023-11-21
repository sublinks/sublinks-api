package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRemovedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(Post post) {

    PostRemovedEvent postRemovedEvent = new PostRemovedEvent(this, post);
    applicationEventPublisher.publishEvent(postRemovedEvent);
  }
}
