package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.entities.PostReport;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostReportUpdatedEvent extends ApplicationEvent {

  private final PostReport postReport;

  public PostReportUpdatedEvent(final Object source, final PostReport postReport) {

    super(source);
    this.postReport = postReport;
  }
}
