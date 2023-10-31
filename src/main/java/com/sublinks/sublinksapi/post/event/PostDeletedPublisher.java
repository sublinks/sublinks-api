package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDeletedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(Post post) {

        PostDeletedEvent postDeletedEvent = new PostDeletedEvent(this, post);
        applicationEventPublisher.publishEvent(postDeletedEvent);
    }
}
