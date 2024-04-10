package com.sublinks.sublinksapi.email.listeners;

import com.sublinks.sublinksapi.comment.events.CommentReportCreatedEvent;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentReportCreatedListener implements ApplicationListener<CommentReportCreatedEvent> {

  private final LocalInstanceContext localInstanceContext;
  private final PersonService personService;


  @Override
  @Transactional
  public void onApplicationEvent(CommentReportCreatedEvent event) {
    // @todo: implement on report created email on report created (post,comment,dm)
  }
}
