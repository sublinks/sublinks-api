package com.sublinks.sublinksapi.private_messages.events;

import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PrivateMessageDeletedEvent extends ApplicationEvent {
    private final PrivateMessage privateMessage;

    public PrivateMessageDeletedEvent(final Object source, final PrivateMessage privateMessage) {

        super(source);
        this.privateMessage = privateMessage;
    }
}
