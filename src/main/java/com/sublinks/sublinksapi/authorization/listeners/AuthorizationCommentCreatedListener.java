package com.sublinks.sublinksapi.authorization.listeners;

import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.person.entities.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * AuthorizationCommentCreatedListener is a class that listens to CommentCreatedEvent and performs
 * authorization logic.
 */
@Component
@RequiredArgsConstructor
public class AuthorizationCommentCreatedListener implements
    ApplicationListener<CommentCreatedEvent> {

  @Override
  public void onApplicationEvent(CommentCreatedEvent event) {

    Person person = event.getComment().getPerson();
  }
}
