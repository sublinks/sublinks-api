package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.PostSave;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSaveDeletedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(PostSave postSave) {

        PostSaveDeletedEvent postSaveDeletedEvent = new PostSaveDeletedEvent(this, postSave);
        applicationEventPublisher.publishEvent(postSaveDeletedEvent);
    }
}
