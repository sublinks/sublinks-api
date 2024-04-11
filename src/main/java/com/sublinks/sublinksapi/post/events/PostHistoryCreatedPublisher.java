package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.PostHistory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PostHistoryCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PostHistoryCreatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(PostHistory postHistory) {

    PostHistoryCreatedEvent postHistoryCreatedEvent = new PostHistoryCreatedEvent(this,
        postHistory);
    applicationEventPublisher.publishEvent(postHistoryCreatedEvent);
  }
}
