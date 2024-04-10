package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionView;
import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentAggregate;
import com.sublinks.sublinksapi.comment.entities.CommentLike;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMention;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPersonMentionService {

  private final ConversionService conversionService;
  private final LemmyCommunityService lemmyCommunityService;
  private final CommentLikeService commentLikeService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  public PersonMentionView getPersonMentionView(PersonMention personMention) {

    final Comment comment = personMention.getComment();
    final Person creator = comment.getPerson();
    final CommentAggregate commentAggregates = comment.getCommentAggregate();

    final CommentLike commentLike = commentLikeService.getCommentLike(comment,
        personMention.getRecipient()).orElse(null);

    //@todo: Check if person is blocked and comment is saved
    return PersonMentionView.builder()
        .person_mention(conversionService.convert(personMention,
            com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMention.class))
        .counts(conversionService.convert(commentAggregates, CommentAggregates.class))
        .creator(conversionService.convert(creator,
                com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
        .creator_blocked(false) // @todo check if user is blocked
        .comment(conversionService.convert(comment,
            com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment.class)).post(
            conversionService.convert(comment.getPost(),
                com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post.class))
        .saved(false) // @todo check if comment is saved
        .recipient(conversionService.convert(personMention.getRecipient(),
            com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
        .community(conversionService.convert(comment.getCommunity(),
                com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
        .my_vote(commentLike == null ? 0 : commentLike.getScore())
        .creator_banned_from_community(
            linkPersonCommunityService.hasLink(creator, comment.getCommunity(),
                LinkPersonCommunityType.banned)).subscribed(
            lemmyCommunityService.getPersonCommunitySubscribeType(creator, comment.getCommunity()))
        .creator_is_admin(false) // @todo check if user is admin
        .creator_is_moderator(false) // @todo check if user is moderator
        .build();
  }
}
