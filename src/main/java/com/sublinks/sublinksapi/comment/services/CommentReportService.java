package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentReportCreatedEvent;
import com.sublinks.sublinksapi.comment.events.CommentReportCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentReportUpdatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentReportService {

  private final CommentReportRepository commentReportRepository;
  private final CommentReportCreatedPublisher commentCreatedPublisher;
  private final CommentReportUpdatedPublisher commentReportUpdatedPublisher;

  @Transactional
  public void createCommentReport(final CommentReport commentReport) {

    commentReportRepository.save(commentReport);
    commentCreatedPublisher.publish(commentReport);
  }

  @Transactional
  public void updateCommentReport(final CommentReport commentReport) {

    commentReportRepository.save(commentReport);
    commentReportUpdatedPublisher.publish(commentReport);
  }
}
