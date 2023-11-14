package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.PostLike;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostLikeCreatedEvent extends ApplicationEvent {
    private final PostLike postLike;

    public PostLikeCreatedEvent(final Object source, final PostLike postLike) {

        super(source);
        this.postLike = postLike;
    }
}
