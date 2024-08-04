package com.sublinks.sublinksapi.api.lemmy.v3.comment.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReport;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyCommentReportService {

  private final ConversionService conversionService;
  private final CommentLikeService commentLikeService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  @NonNull
  public CommentReportView createCommentReportView(
      final com.sublinks.sublinksapi.comment.entities.CommentReport commentReport,
      final com.sublinks.sublinksapi.person.entities.Person Person) {

    return commentViewBuilder(commentReport, Person).build();
  }

  @NonNull
  private CommentReportView.CommentReportViewBuilder commentViewBuilder(
      final com.sublinks.sublinksapi.comment.entities.CommentReport commentReport,
      final com.sublinks.sublinksapi.person.entities.Person Person) {

    final CommentReport lemmyCommentReport = conversionService.convert(commentReport,
        CommentReport.class);

    final Person lemmyCommentCreator = conversionService.convert(commentReport.getComment()
        .getPerson(), Person.class);
    final Person lemmyResolver = conversionService.convert(commentReport.getResolver(),
        Person.class);

    final com.sublinks.sublinksapi.person.entities.Person creator = commentReport.getCreator();
    final Person lemmyCreator = conversionService.convert(creator, Person.class);

    final Comment lemmyComment = conversionService.convert(commentReport.getComment(),
        Comment.class);

    final CommentAggregates commentAggregates = conversionService.convert(commentReport.getComment()
        .getCommentAggregate(), CommentAggregates.class);

    final Post lemmyPost = conversionService.convert(commentReport.getComment()
        .getPost(), Post.class);

    final Community lemmyCommunity = conversionService.convert(commentReport.getComment()
        .getCommunity(), Community.class);

    final int personVote = commentLikeService.getPersonCommentVote(Person,
        commentReport.getComment());

    final boolean creatorBannedFromCommunity = linkPersonCommunityService.hasLink(
        commentReport.getComment()
            .getCommunity(), creator, LinkPersonCommunityType.banned);

    return CommentReportView.builder()
        .creator(lemmyCreator)
        .comment(lemmyComment)
        .post(lemmyPost)
        .comment_report(lemmyCommentReport)
        .comment_creator(lemmyCommentCreator)
        .community(lemmyCommunity)
        .my_vote(personVote)
        .resolver(lemmyResolver)
        .counts(commentAggregates)
        .creator_banned_from_community(creatorBannedFromCommunity);
  }
}
