package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentReport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReportCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentReport commentReport) {

    final CommentReportCreatedEvent commentReportCreatedEvent = new CommentReportCreatedEvent(this,
        commentReport);
    applicationEventPublisher.publishEvent(commentReportCreatedEvent);
  }
}
