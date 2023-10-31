package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.LinkPersonPost;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonPostDeletedEvent extends ApplicationEvent {
    private final LinkPersonPost linkPersonPost;

    public LinkPersonPostDeletedEvent(
            final Object source,
            final LinkPersonPost linkPersonPost
    ) {
        super(source);
        this.linkPersonPost = linkPersonPost;
    }
}
