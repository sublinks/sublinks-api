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

  /**
   * Creates a new comment reply and publishes an event indicating its creation.
   *
   * @param commentReply The CommentReply object to be created.
   */
  public void createCommentReply(CommentReply commentReply) {

    commentReplyRepository.save(commentReply);
    commentReplyCreatedPublisher.publish(commentReply);
  }

  /**
   * Deletes a comment from comment_replies
   *
   * @param commentId The ID for the comment to be deleted.
   */
  public void deleteOneByCommentId(Long commentId) {
    commentReplyRepository.deleteByCommentId(commentId);
  }

  /**
   * Updates an existing comment reply and publishes an event indicating its
   * update.
   *
   * @param commentReply The CommentReply object to be updated.
   */
  public void updateCommentReply(CommentReply commentReply) {

    commentReplyRepository.save(commentReply);
    commentReplyUpdatedPublisher.publish(commentReply);
  }
}
