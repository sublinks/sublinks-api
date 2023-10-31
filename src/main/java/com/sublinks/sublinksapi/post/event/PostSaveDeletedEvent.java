package com.sublinks.sublinksapi.post.event;

import com.sublinks.sublinksapi.post.PostSave;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostSaveDeletedEvent extends ApplicationEvent {
    private final PostSave postSave;

    public PostSaveDeletedEvent(final Object source, final PostSave postSave) {
        super(source);
        this.postSave = postSave;
    }
}
