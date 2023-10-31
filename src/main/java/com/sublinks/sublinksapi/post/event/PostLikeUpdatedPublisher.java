package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeUpdatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(PostLike postLike) {

        PostLikeUpdatedEvent postLikeUpdatedEvent = new PostLikeUpdatedEvent(this, postLike);
        applicationEventPublisher.publishEvent(postLikeUpdatedEvent);
    }
}
