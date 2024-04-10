package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostDeletedEvent extends ApplicationEvent {

  private final Post post;

  public PostDeletedEvent(final Object source, final Post post) {

    super(source);
    this.post = post;
  }
}
