package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.events.PostReportCreatedPublisher;
import com.sublinks.sublinksapi.post.events.PostReportUpdatedPublisher;
import com.sublinks.sublinksapi.post.repositories.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReportService {

  private final PostReportRepository postReportRepository;
  private final PostReportCreatedPublisher postReportCreatedPublisher;
  private final PostReportUpdatedPublisher postReportUpdatedPublisher;


  @Transactional
  public void createPostReport(final PostReport postReport) {

    postReportRepository.save(postReport);
    postReportCreatedPublisher.publish(postReport);
  }

  public void updatePostReport(final PostReport postReport) {

    postReportRepository.save(postReport);
    postReportUpdatedPublisher.publish(postReport);
  }
}
