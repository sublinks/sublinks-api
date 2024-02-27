package com.sublinks.sublinksapi.post.listeners;

import com.sublinks.sublinksapi.post.config.PostHistoryConfig;
import com.sublinks.sublinksapi.post.events.PostCreatedEvent;
import com.sublinks.sublinksapi.post.services.PostHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostCreatedForHistoryListener implements ApplicationListener<PostCreatedEvent> {

  private final PostHistoryConfig postHistoryConfig;
  private final PostHistoryService postHistoryService;

  @Override
  @Transactional
  public void onApplicationEvent(@NonNull PostCreatedEvent event) {

    if (!postHistoryConfig.isKeepPostHistory()) {
      return;
    }

    try {
      postHistoryService.createOrAddPostHistory(event.getPost());
    } catch (Exception ignored) {
      // @todo log this
    }
  }
}
