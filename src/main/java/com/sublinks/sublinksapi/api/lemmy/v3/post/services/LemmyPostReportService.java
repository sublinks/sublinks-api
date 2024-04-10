package com.sublinks.sublinksapi.api.lemmy.v3.post.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportView;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.entities.PostLike;
import com.sublinks.sublinksapi.post.entities.PostReport;
import com.sublinks.sublinksapi.post.services.PostLikeService;
import com.sublinks.sublinksapi.post.services.PostReportService;
import com.sublinks.sublinksapi.post.services.PostSaveService;
import com.sublinks.sublinksapi.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPostReportService {

  private final PostService postService;
  private final PostReportService postReportService;
  private final PostSaveService postSaveService;
  private final PostLikeService postLikeService;
  private final ConversionService conversionService;
  private final LinkPersonCommunityService linkPersonCommunityService;


  public PostReportView postReportViewFromPost(final PostReport postReport,
      final Person person) {

    return postReportViewBuilder(postReport, person).build();
  }

  private PostReportView.PostReportViewBuilder postReportViewBuilder(final PostReport postReport,
      final Person person) {

    final com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReport lemmyPostReport = conversionService.convert(
        postReport, com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReport.class);

    final Post lemmyPost = conversionService.convert(postReport.getPost(), Post.class);

    final Person creator = postReport.getCreator();

    final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person lemmyCreator = conversionService.convert(
        creator, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class);

    final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person resolver = conversionService.convert(
        postReport.getResolver(), com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class);

    final Community community = conversionService.convert(postReport.getPost().getCommunity(),
        Community.class);

    final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person lemmyPostCreator = conversionService.convert(
        postService.getPostCreator(postReport.getPost()),
        com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class);

    final int personVote = postLikeService.getPostLike(postReport.getPost(), person)
        .map(PostLike::getScore).orElse(0);

    final PostAggregates counts = conversionService.convert(postReport.getPost().getPostAggregate(),
        PostAggregates.class);

    final boolean creatorBannedFromCommunity = linkPersonCommunityService.hasLink(creator,
        postReport.getPost().getCommunity(), LinkPersonCommunityType.banned);

    return PostReportView.builder().post(lemmyPost).creator(lemmyCreator).community(community)
        .post_report(lemmyPostReport).post_creator(lemmyPostCreator).my_vote(personVote)
        .resolver(resolver).counts(counts)
        .creator_banned_from_community(creatorBannedFromCommunity);
  }
}
