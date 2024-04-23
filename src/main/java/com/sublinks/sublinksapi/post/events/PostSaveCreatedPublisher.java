package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.PostSave;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSaveCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(PostSave postSave) {

    PostSaveCreatedEvent postSaveCreatedEvent = new PostSaveCreatedEvent(this, postSave);
    applicationEventPublisher.publishEvent(postSaveCreatedEvent);
  }
}
