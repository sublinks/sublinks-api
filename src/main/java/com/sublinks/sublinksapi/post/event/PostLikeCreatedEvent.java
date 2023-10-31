package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.PostLike;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostLikeCreatedEvent extends ApplicationEvent {
    private PostLike postLike;

    public PostLikeCreatedEvent(final Object source, final PostLike postLike) {
        super(source);
        this.postLike = postLike;
    }
}
