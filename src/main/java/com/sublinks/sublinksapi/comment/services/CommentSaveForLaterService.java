package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentReport;
import com.sublinks.sublinksapi.comment.entities.CommentSaveForLater;
import com.sublinks.sublinksapi.comment.events.CommentReportCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentReportUpdatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentSaveForLaterCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentSaveForLaterDeletedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentSaveForLaterRepository;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentSaveForLaterService {

  private final CommentSaveForLaterRepository commentSaveForLaterRepository;
  private final CommentSaveForLaterCreatedPublisher commentSaveForLaterCreatedPublisher;
  private final CommentSaveForLaterDeletedPublisher commentSaveForLaterDeletedPublisher;

  @Transactional
  public void createCommentSaveForLater(final CommentSaveForLater commentSaveForLater) {

    commentSaveForLaterRepository.save(commentSaveForLater);
    commentSaveForLaterCreatedPublisher.publish(commentSaveForLater);
  }

  @Transactional
  public void deleteCommentSaveForLater(final CommentSaveForLater commentSaveForLater) {

    commentSaveForLaterRepository.delete(commentSaveForLater);
    commentSaveForLaterDeletedPublisher.publish(commentSaveForLater);
  }
}
