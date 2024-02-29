package com.sublinks.sublinksapi.comment.listeners;

import com.sublinks.sublinksapi.comment.config.CommentHistoryConfig;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.comment.services.CommentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentCreatedForHistoryListener implements ApplicationListener<CommentCreatedEvent> {

  private final CommentHistoryConfig commentHistoryConfig;
  private final CommentHistoryService commentHistoryService;

  @Override
  @Transactional
  public void onApplicationEvent(@NonNull CommentCreatedEvent event) {

    if (!commentHistoryConfig.isKeepCommentHistory()) {
      return;
    }

    try {
      commentHistoryService.createOrAddCommentHistory(event.getComment());
    } catch (Exception ignored) {
      // @todo log this
    }
  }
}
