package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.PostSave;
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
