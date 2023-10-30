package com.sublinks.sublinksapi.person.event;

import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonCommunityCreatedEvent extends ApplicationEvent {
    private final LinkPersonCommunity linkPersonCommunity;

    public LinkPersonCommunityCreatedEvent(
            final Object source,
            final LinkPersonCommunity linkPersonCommunity
    ) {
        super(source);
        this.linkPersonCommunity = linkPersonCommunity;
    }
}
