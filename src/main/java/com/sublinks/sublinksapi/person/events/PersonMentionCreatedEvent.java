package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PersonMentionCreatedEvent extends ApplicationEvent {

  private final PersonMention personMention;

  public PersonMentionCreatedEvent(final Object source, final PersonMention personMention) {

    super(source);
    this.personMention = personMention;
  }
}
