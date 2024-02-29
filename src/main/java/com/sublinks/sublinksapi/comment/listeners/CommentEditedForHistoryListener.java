package com.sublinks.sublinksapi.comment.listeners;

import com.sublinks.sublinksapi.comment.config.CommentHistoryConfig;
import com.sublinks.sublinksapi.comment.events.CommentUpdatedEvent;
import com.sublinks.sublinksapi.comment.services.CommentHistoryService;
import com.sublinks.sublinksapi.post.config.PostHistoryConfig;
import com.sublinks.sublinksapi.post.events.PostUpdatedEvent;
import com.sublinks.sublinksapi.post.services.PostHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentEditedForHistoryListener implements ApplicationListener<CommentUpdatedEvent> {

  private final CommentHistoryConfig commentHistoryConfig;
  private final CommentHistoryService commentHistoryService;

  @Override
  @Transactional
  public void onApplicationEvent(@NonNull CommentUpdatedEvent event) {

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
