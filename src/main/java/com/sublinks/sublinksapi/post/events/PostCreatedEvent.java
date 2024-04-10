package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostCreatedEvent extends ApplicationEvent {

  private final Post post;

  public PostCreatedEvent(final Object source, final Post post) {

    super(source);
    this.post = post;
  }
}
