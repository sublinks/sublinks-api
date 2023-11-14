package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentUpdatedEvent extends ApplicationEvent {
    private final Comment comment;

    public CommentUpdatedEvent(final Object source, final Comment comment) {

        super(source);
        this.comment = comment;
    }
}
