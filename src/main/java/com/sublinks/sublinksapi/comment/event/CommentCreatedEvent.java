package com.sublinks.sublinksapi.comment.event;

import com.sublinks.sublinksapi.comment.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentCreatedEvent extends ApplicationEvent {
    private final Comment comment;

    public CommentCreatedEvent(final Object source, Comment comment) {
        super(source);
        this.comment = comment;
    }
}
