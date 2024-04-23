package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.PostSave;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostSaveDeletedEvent extends ApplicationEvent {

  private final PostSave postSave;

  public PostSaveDeletedEvent(final Object source, final PostSave postSave) {

    super(source);
    this.postSave = postSave;
  }
}
