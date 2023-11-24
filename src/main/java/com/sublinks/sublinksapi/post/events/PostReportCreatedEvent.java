package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostReport;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostReportCreatedEvent extends ApplicationEvent {

  private final PostReport postReport;

  public PostReportCreatedEvent(final Object source, final PostReport postReport) {

    super(source);
    this.postReport = postReport;
  }
}
