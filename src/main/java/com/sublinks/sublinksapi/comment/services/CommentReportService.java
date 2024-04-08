package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.events.CommentReportCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentReportUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentReportService {

  private final CommentReportRepository commentReportRepository;
  private final CommentReportCreatedPublisher commentCreatedPublisher;
  private final CommentReportUpdatedPublisher commentReportUpdatedPublisher;

  /**
   * Creates a new report for a comment and publishes an event upon creation.
   *
   * @param commentReport The CommentReport object representing the report to be created.
   */
  @Transactional
  public void createCommentReport(final CommentReport commentReport) {

    commentReportRepository.save(commentReport);
    commentCreatedPublisher.publish(commentReport);
  }

  /**
   * Updates an existing comment report and publishes an event upon update.
   *
   * @param commentReport The CommentReport object representing the report to be updated.
   */
  @Transactional
  public void updateCommentReport(final CommentReport commentReport) {

    commentReportRepository.save(commentReport);
    commentReportUpdatedPublisher.publish(commentReport);
  }

  @Transactional
  public void resolveAllReportsByCommentCreator(final Person creator, final Person resolver) {

    commentReportRepository.resolveAllReportsByCommentCreator(creator, resolver);
  }

  @Transactional
  public void resolveAllReportsByCommentCreatorAndCommunity(final Person creator,
      final Community community, final Person resolver) {

    commentReportRepository.resolveAllReportsByCommentCreatorAndCommunity(creator, community,
        resolver);
  }

  @Transactional
  public void resolveAllReportsByComment(final Comment comment, final Person resolver) {

    commentReportRepository.resolveAllReportsByComment(comment, resolver);
  }
}
