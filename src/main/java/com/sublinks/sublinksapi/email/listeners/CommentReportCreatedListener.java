package com.sublinks.sublinksapi.email.listeners;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.comment.events.CommentReportCreatedEvent;
import com.sublinks.sublinksapi.comment.services.CommentReplyService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import java.util.Optional;
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
