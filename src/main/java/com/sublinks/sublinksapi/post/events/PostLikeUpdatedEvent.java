package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.PostLike;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostLikeUpdatedEvent extends ApplicationEvent {
    private final PostLike postLike;
    private final Action action;

    public PostLikeUpdatedEvent(final Object source, final PostLike postLike, final Action action) {

        super(source);
        this.postLike = postLike;
        this.action = action;
    }

    public enum Action {
        FROM_DOWN_TO_UP,
        FROM_DOWN_TO_NEUTRAL,
        FROM_UP_TO_DOWN,
        FROM_UP_TO_NEUTRAL,
        FROM_NEUTRAL_TO_UP,
        FROM_NEUTRAL_TO_DOWN,
        NO_CHANGE
    }
}
