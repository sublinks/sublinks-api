package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.events.CommentReplyCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentReplyUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReplyService {

  private final CommentReplyRepository commentReplyRepository;
  private final CommentReplyCreatedPublisher commentReplyCreatedPublisher;
  private final CommentReplyUpdatedPublisher commentReplyUpdatedPublisher;

  public void createCommentReply(CommentReply commentReply) {

    commentReplyRepository.save(commentReply);
    commentReplyCreatedPublisher.publish(commentReply);
  }

  public void updateCommentReply(CommentReply commentReply) {

    commentReplyRepository.save(commentReply);
    commentReplyUpdatedPublisher.publish(commentReply);
  }
}
