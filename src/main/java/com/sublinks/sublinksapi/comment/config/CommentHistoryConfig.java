package com.sublinks.sublinksapi.comment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CommentHistoryConfig {

  @Value("${sublinks.keep_comment_history}")
  private boolean keepCommentHistory;
}
