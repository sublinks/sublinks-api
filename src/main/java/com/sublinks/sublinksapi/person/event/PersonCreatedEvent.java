package com.sublinks.sublinksapi.person.event;

import com.sublinks.sublinksapi.person.Person;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonCreatedEvent extends ApplicationEvent {
    private final Person person;

    public PersonCreatedEvent(Object source, Person person) {
        super(source);
        this.person = person;
    }
}
