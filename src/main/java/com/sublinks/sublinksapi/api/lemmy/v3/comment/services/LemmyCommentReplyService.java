package com.sublinks.sublinksapi.api.lemmy.v3.comment.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReply;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReplyView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LemmyCommentReplyService {

  private final ConversionService conversionService;
  private final CommentLikeService commentLikeService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  @NonNull
  public CommentReplyView createCommentReplyView(
      final com.sublinks.sublinksapi.comment.dto.CommentReply commentReply,
      final com.sublinks.sublinksapi.person.dto.Person Person) {

    return commentReplyViewBuilder(commentReply, Person).build();
  }

  @NonNull
  private CommentReplyView.CommentReplyViewBuilder commentReplyViewBuilder(
      final com.sublinks.sublinksapi.comment.dto.CommentReply commentReply,
      final com.sublinks.sublinksapi.person.dto.Person Person) {

    final CommentReplyView.CommentReplyViewBuilder commentReplyViewBuilder = CommentReplyView.builder();

    final com.sublinks.sublinksapi.person.dto.Person creator = commentReply.getComment()
        .getPerson();

    final Person lemmyCreator = conversionService.convert(creator, Person.class);

    final Comment lemmyComment = conversionService.convert(commentReply.getComment(),
        Comment.class);

    final CommentAggregates commentAggregates = conversionService.convert(
        commentReply.getComment().getCommentAggregate(), CommentAggregates.class);

    final Post lemmyPost = conversionService.convert(commentReply.getComment().getPost(),
        Post.class);

    final Community lemmyCommunity = conversionService.convert(
        commentReply.getComment().getCommunity(), Community.class);

    final int personVote = commentLikeService.getPersonCommentVote(Person,
        commentReply.getComment());

    final CommentReply lemmyCommentReply = conversionService.convert(commentReply,
        CommentReply.class);

    final boolean creatorBannedFromCommunity = linkPersonCommunityService.hasLink(creator,
        commentReply.getComment().getCommunity(), LinkPersonCommunityType.banned);

    //@todo: Check if comment is saved or blocked
    return commentReplyViewBuilder
        .comment_reply(lemmyCommentReply)
        .creator(lemmyCreator)
        .comment(lemmyComment)
        .counts(commentAggregates)
        .post(lemmyPost)
        .community(lemmyCommunity)
        .my_vote(personVote)
        .recipient(conversionService.convert(commentReply.getRecipient(), Person.class))
        .subscribed(linkPersonCommunityService.hasLink(Person,
            commentReply.getComment().getCommunity(), LinkPersonCommunityType.follower))
        .creator_banned_from_community(creatorBannedFromCommunity)
        .creator_is_moderator(false) // @todo: Check if creator is moderator
        .creator_is_admin(false) // @todo: Check if creator is admin
        .creator_blocked(false)
        .saved(false);

  }
}
