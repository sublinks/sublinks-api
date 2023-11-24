package com.sublinks.sublinksapi.comment.listeners;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.comment.services.CommentReplyService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentCreatedListener implements ApplicationListener<CommentCreatedEvent> {

  private final CommentReplyService commentReplyService;
  private final CommentService commentService;

  @Override
  @Transactional
  public void onApplicationEvent(CommentCreatedEvent event) {

    Optional<Comment> parent = commentService.getParentComment(event.getComment());

    if (parent.isEmpty() || parent.get().getPerson().equals(event.getComment().getPerson())) {
      return;
    }

    final CommentReply commentReply = CommentReply.builder().recipient(parent.get().getPerson())
        .comment(event.getComment()).isRead(false).build();

    commentReplyService.createCommentReply(commentReply);
  }
}
