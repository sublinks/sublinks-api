package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.queue.services.Producer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class CommentCreateListener implements ApplicationListener<CommentCreatedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(CommentCreateListener.class);

  final private Producer federationProducer;
  @Value("${sublinks.federation.exchange}")
  private String federationExchange;

  @Override
  public void onApplicationEvent(@NonNull CommentCreatedEvent event) {

    if (getFederationProducer() == null) {
      logger.error("federation producer is not instantiated properly");
      return;
    }

    Comment comment = event.getComment();

    final com.sublinks.sublinksapi.federation.models.Comment commentMsg =
        com.sublinks.sublinksapi.federation.models.Comment.builder()
            .id(comment.getActivityPubId())
            .author_id(comment.getPerson()
                .getActivityPubId())
            .content(comment.getCommentBody())
            .post_id(comment.getPost()
                .getActivityPubId())
            .url_stub(comment.getPath())
            .published(comment.getCreatedAt())
            .build();

    getFederationProducer().sendMessage(getFederationExchange(),
        RoutingKey.COMMENT_CREATE.getValue(),
        commentMsg);
    logger.info(String.format("comment created %s", comment.getActivityPubId()));
  }
}
