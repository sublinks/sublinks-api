package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
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


  @Transactional
  public void resolveAllReportsByPerson(final Person person, final Person resolver) {

    postReportRepository.resolveAllPostReportsByPerson(person, resolver);
  }

  @Transactional
  public void resolveAllReportsByPost(final Post post, final Person resolver) {

    postReportRepository.resolveAllReportsByPost(post, resolver);
  }

  @Transactional
  public void resolveAllReportsByPersonAndCommunity(final Person person, final Community community,
      final Person resolver) {

    postReportRepository.resolveAllPostReportsByPersonAndCommunity(person, community, resolver);
  }
}
