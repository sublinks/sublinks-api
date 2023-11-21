package com.sublinks.sublinksapi.authorization.listeners;

import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.person.dto.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationCommentCreatedListener implements
    ApplicationListener<CommentCreatedEvent> {

  private final AuthorizationService authorizationService;


  @Override
  public void onApplicationEvent(CommentCreatedEvent event) {

    Person person = event.getComment().getPerson();
    authorizationService.allowPerson(person)
        .performTheAction(AuthorizeAction.delete)
        .performTheAction(AuthorizeAction.update)
        .onEntity(event.getComment());
  }
}
