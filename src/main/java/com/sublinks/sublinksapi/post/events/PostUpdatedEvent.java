package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.Post;
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
