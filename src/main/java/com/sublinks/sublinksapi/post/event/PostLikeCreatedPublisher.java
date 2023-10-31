package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeCreatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(PostLike postLike) {

        PostLikeCreatedEvent postLikeCreatedEvent = new PostLikeCreatedEvent(this, postLike);
        applicationEventPublisher.publishEvent(postLikeCreatedEvent);
    }
}
