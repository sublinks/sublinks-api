package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.entities.CommentReport;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentReportUpdatedEvent extends ApplicationEvent {

  private final CommentReport commentReport;

  public CommentReportUpdatedEvent(final Object source, final CommentReport commentReport) {

    super(source);
    this.commentReport = commentReport;
  }
}
