package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.Person;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonDeletedEvent extends ApplicationEvent {
    private final Person person;

    public PersonDeletedEvent(final Object source, final Person person) {
        super(source);
        this.person = person;
    }
}
