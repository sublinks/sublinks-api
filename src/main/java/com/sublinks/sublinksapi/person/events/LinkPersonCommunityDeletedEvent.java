package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonCommunityDeletedEvent extends ApplicationEvent {
    private final LinkPersonCommunity linkPersonCommunity;

    public LinkPersonCommunityDeletedEvent(
            final Object source,
            final LinkPersonCommunity linkPersonCommunity
    ) {

        super(source);
        this.linkPersonCommunity = linkPersonCommunity;
    }
}
