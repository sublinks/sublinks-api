package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentReport;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentReportCreatedEvent extends ApplicationEvent {

  private final CommentReport commentReport;

  public CommentReportCreatedEvent(final Object source, final CommentReport commentReport) {

    super(source);
    this.commentReport = commentReport;
  }
}
