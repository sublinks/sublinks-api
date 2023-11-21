package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PersonMentionUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PersonMentionUpdatedPublisher(final ApplicationEventPublisher applicationEventPublisher) {

    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(PersonMention person) {

    PersonMentionupdatedEvent personMentionupdatedEvent = new PersonMentionupdatedEvent(this,
        person);
    applicationEventPublisher.publishEvent(personMentionupdatedEvent);
  }
}
