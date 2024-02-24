package com.sublinks.sublinksapi.api.lemmy.v3.comment.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyCommentService {

  private final LemmyCommunityService lemmyCommunityService;
  private final LocalInstanceContext localInstanceContext;
  private final ConversionService conversionService;
  private final CommentLikeService commentLikeService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  public String generateActivityPubId(final com.sublinks.sublinksapi.comment.dto.Comment comment) {

    String domain = localInstanceContext.instance().getDomain();
    return String.format("%s/comment/%d", domain, comment.getId());
  }


  @NonNull
  public CommentView createCommentView(final com.sublinks.sublinksapi.comment.dto.Comment comment,
      final com.sublinks.sublinksapi.person.dto.Person person) {

    return commentViewBuilder(comment, person).build();
  }

  @NonNull
  public CommentView createCommentView(final com.sublinks.sublinksapi.comment.dto.Comment comment) {

    return commentViewBuilder(comment).build();
  }

  @NonNull
  private CommentView.CommentViewBuilder commentViewBuilder(
      final com.sublinks.sublinksapi.comment.dto.Comment comment,
      final com.sublinks.sublinksapi.person.dto.Person person) {

    CommentView.CommentViewBuilder commentView = commentViewBuilder(comment);

    final SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(
        person, comment.getCommunity());
    final int personVote = commentLikeService.getPersonCommentVote(person, comment);

    commentView.subscribed(subscribedType).saved(false)// @todo check if saved
        .my_vote(personVote);

    return commentView;
  }

  @NonNull
  private CommentView.CommentViewBuilder commentViewBuilder(
      final com.sublinks.sublinksapi.comment.dto.Comment comment) {

    final Comment lemmyComment = conversionService.convert(comment, Comment.class);

    final com.sublinks.sublinksapi.person.dto.Person creator = comment.getPerson();
    final Person lemmyCreator = conversionService.convert(creator, Person.class);

    final Community lemmyCommunity = conversionService.convert(comment.getCommunity(),
        Community.class);
    final Post lemmyPost = conversionService.convert(comment.getPost(), Post.class);

    CommentAggregate commentAggregate = comment.getCommentAggregate();
    if (comment.getCommentAggregate() == null) {
      commentAggregate = CommentAggregate.builder().build();
    }

    final CommentAggregates lemmyCommentAggregates = conversionService.convert(commentAggregate,
        CommentAggregates.class);

    final boolean isBannedFromCommunity = linkPersonCommunityService.hasLink(creator,
        comment.getCommunity(), LinkPersonCommunityType.banned);

    return CommentView.builder().comment(lemmyComment).creator(lemmyCreator)
        .community(lemmyCommunity).post(lemmyPost).counts(lemmyCommentAggregates)
        .creator_banned_from_community(isBannedFromCommunity).creator_blocked(false)
        .creator_is_moderator(false) // @todo check if creator is moderator
        .creator_is_admin(false); // @todo check if creator is admin
  }
}
