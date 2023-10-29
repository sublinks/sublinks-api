package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostCreatedEvent extends ApplicationEvent {
    final private Post post;

    public PostCreatedEvent(final Object source, final Post post) {
        super(source);
        this.post = post;
    }
}
