package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentLike;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentLikeCreatedEvent extends ApplicationEvent {
    private final CommentLike commentLike;

    public CommentLikeCreatedEvent(final Object source, final CommentLike commentLike) {
        super(source);
        this.commentLike = commentLike;
    }
}
