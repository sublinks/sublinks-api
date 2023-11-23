package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentReport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReportUpdatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final CommentReport commentReport) {

    final CommentReportUpdatedEvent commentReportUpdatedEvent= new CommentReportUpdatedEvent(this,
        commentReport);
    applicationEventPublisher.publishEvent(commentReportUpdatedEvent);
  }
}
