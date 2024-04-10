package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.PostHistory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostHistoryCreatedEvent extends ApplicationEvent {

  private final PostHistory postHistory;

  public PostHistoryCreatedEvent(final Object source, final PostHistory postHistory) {

    super(source);
    this.postHistory = postHistory;
  }
}
