package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.PostLike;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostLikeUpdatedEvent extends ApplicationEvent {
    private final PostLike postLike;

    public PostLikeUpdatedEvent(final Object source, final PostLike postLike) {
        super(source);
        this.postLike = postLike;
    }
}
