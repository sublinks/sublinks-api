package com.sublinks.sublinksapi.person.event;

import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonCommunityUpdatedEvent extends ApplicationEvent {
    private final LinkPersonCommunity linkPersonCommunity;

    public LinkPersonCommunityUpdatedEvent(final Object source, final LinkPersonCommunity linkPersonCommunity) {
        super(source);
        this.linkPersonCommunity = linkPersonCommunity;
    }
}
