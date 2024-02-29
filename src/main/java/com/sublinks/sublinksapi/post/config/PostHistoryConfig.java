package com.sublinks.sublinksapi.post.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PostHistoryConfig {

  @Value("${sublinks.keep_post_history}")
  private boolean keepPostHistory;
}
