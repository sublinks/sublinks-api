package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.Comment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CommentCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CommentCreatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(final Comment comment) {

        final CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(this, comment);
        applicationEventPublisher.publishEvent(commentCreatedEvent);
    }
}
