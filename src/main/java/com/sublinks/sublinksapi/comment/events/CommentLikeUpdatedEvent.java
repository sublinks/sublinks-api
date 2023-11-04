package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentLike;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentLikeUpdatedEvent extends ApplicationEvent {
    private final CommentLike commentLike;
    private final Action action;

    public CommentLikeUpdatedEvent(final Object source, final CommentLike commentLike, final Action action) {
        super(source);
        this.commentLike = commentLike;
        this.action = action;
    }

    public enum Action {
        FROM_DOWN_TO_UP,
        FROM_DOWN_TO_NEUTRAL,
        FROM_UP_TO_DOWN,
        FROM_UP_TO_NEUTRAL,
        FROM_NEUTRAL_TO_UP,
        FROM_NEUTRAL_TO_DOWN
    }
}
