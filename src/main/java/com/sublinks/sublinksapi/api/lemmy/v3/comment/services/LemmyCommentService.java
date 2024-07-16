package com.sublinks.sublinksapi.api.lemmy.v3.comment.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.comment.entities.CommentAggregate;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.comment.services.CommentSaveService;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import com.sublinks.sublinksapi.person.repositories.LinkPersonInstanceRepository;
import com.sublinks.sublinksapi.person.repositories.LinkPersonPersonRepository;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyCommentService {

  private final LemmyCommunityService lemmyCommunityService;
  private final ConversionService conversionService;
  private final CommentLikeService commentLikeService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final CommentSaveService commentSaveService;
  private final LinkPersonInstanceRepository linkPersonInstanceRepository;
  private final LinkPersonPersonRepository linkPersonPersonRepository;

  @NonNull
  public CommentView createCommentView(
      final com.sublinks.sublinksapi.comment.entities.Comment comment,
      final com.sublinks.sublinksapi.person.entities.Person person) {

    return commentViewBuilder(comment, person).build();
  }

  @NonNull
  public CommentView createCommentView(
      final com.sublinks.sublinksapi.comment.entities.Comment comment) {

    return commentViewBuilder(comment).build();
  }

  @NonNull
  private CommentView.CommentViewBuilder commentViewBuilder(
      final com.sublinks.sublinksapi.comment.entities.Comment comment,
      final com.sublinks.sublinksapi.person.entities.Person person) {

    CommentView.CommentViewBuilder commentView = commentViewBuilder(comment);

    commentView.comment(getComment(comment, person));

    final SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(
        person, comment.getCommunity());
    final int personVote = commentLikeService.getPersonCommentVote(person, comment);

    commentView.subscribed(subscribedType)
        .saved(false)// @todo check if saved
        .creator_blocked(
            linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndToPersonAndLinkType(person,
                    comment.getPerson(), LinkPersonPersonType.blocked)
                .isPresent())
        .my_vote(personVote);

    commentView.saved(commentSaveService.isCommentSavedByPerson(comment, person));

    return commentView;
  }

  @NonNull
  private CommentView.CommentViewBuilder commentViewBuilder(
      final com.sublinks.sublinksapi.comment.entities.Comment comment) {

    final com.sublinks.sublinksapi.person.entities.Person creator = comment.getPerson();
    final Person lemmyCreator = conversionService.convert(creator, Person.class);

    final Community lemmyCommunity = conversionService.convert(comment.getCommunity(),
        Community.class);
    final Post lemmyPost = conversionService.convert(comment.getPost(), Post.class);

    CommentAggregate commentAggregate = comment.getCommentAggregate();
    if (comment.getCommentAggregate() == null) {
      commentAggregate = CommentAggregate.builder()
          .build();
    }

    final CommentAggregates lemmyCommentAggregates = conversionService.convert(commentAggregate,
        CommentAggregates.class);

    final boolean isBannedFromCommunity = linkPersonCommunityService.hasLink(creator,
        comment.getCommunity(), LinkPersonCommunityType.banned);

    final boolean createIsAdmin = RolePermissionService.isAdmin(creator);

    final boolean creatorIsModerator = linkPersonCommunityService.hasLink(creator,
        comment.getCommunity(), LinkPersonCommunityType.moderator);

    return CommentView.builder()
        .comment(getComment(comment, null))
        .creator(lemmyCreator)
        .community(lemmyCommunity)
        .post(lemmyPost)
        .counts(lemmyCommentAggregates)
        .creator_banned_from_community(isBannedFromCommunity)
        .creator_blocked(false)
        .creator_is_moderator(creatorIsModerator)
        .creator_is_admin(createIsAdmin);
  }

  public Comment getComment(final com.sublinks.sublinksapi.comment.entities.Comment comment,
      @Nullable final com.sublinks.sublinksapi.person.entities.Person person) {

    Comment lemmyComment = conversionService.convert(comment, Comment.class);

    if (lemmyComment != null && (lemmyComment.deleted() || lemmyComment.removed())) {
      Comment.CommentBuilder lCommentBuilder = lemmyComment.toBuilder();

      final boolean isAdmin = person != null && person.isAdmin();
      if (!isAdmin) {
        if (lemmyComment.deleted()) {
          lCommentBuilder.content("*Deleted by creator*");
        } else {
          lCommentBuilder.content("*Removed by moderator*");
        }
      }

      lemmyComment = lCommentBuilder.build();
    }

    return lemmyComment;
  }
}
