package com.sublinks.sublinksapi.community.events;

import com.sublinks.sublinksapi.community.dto.Community;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommunityCreatedEvent extends ApplicationEvent {
    private final Community community;

    public CommunityCreatedEvent(Object source, Community community) {
        super(source);
        this.community = community;
    }
}
