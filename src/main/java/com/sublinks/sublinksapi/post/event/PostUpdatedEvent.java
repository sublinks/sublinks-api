package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostUpdatedEvent extends ApplicationEvent {
    private Post post;

    public PostUpdatedEvent(final Object source, final Post post) {
        super(source);
        this.post = post;
    }
}
