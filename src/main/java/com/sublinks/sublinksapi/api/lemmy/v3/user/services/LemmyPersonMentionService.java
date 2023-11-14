package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionView;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonMention;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPersonMentionService {
    private final ConversionService conversionService;
    private final LemmyPostService lemmyPostService;
    private final LemmyCommunityService lemmyCommunityService;
    private final CommentLikeService commentLikeService;
    private final LemmyCommentService lemmyCommentService;

    public PersonMentionView getPersonMentionView(PersonMention personMention) {

        final Comment comment = personMention.getComment();
        final Person creator = comment.getPerson();
        final CommentAggregate commentAggregates = comment.getCommentAggregate();

        final CommentLike commentLike = commentLikeService.getCommentLike(comment, personMention.getRecipient()).orElse(null);

        //@todo: Check if person is blocked, creator is banned and comment is saved
        return PersonMentionView.builder()
                .person_mention(conversionService.convert(personMention, com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMention.class))
                .counts(conversionService.convert(commentAggregates, CommentAggregates.class))
                .creator(conversionService.convert(creator, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
                .creator_blocked(false)
                .comment(conversionService.convert(comment, com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment.class))
                .post(conversionService.convert(comment.getPost(), com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post.class))
                .saved(false)
                .recipient(conversionService.convert(personMention.getRecipient(), com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
                .community(conversionService.convert(comment.getCommunity(), com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))

                .my_vote(commentLike == null ? 0 : commentLike.getScore())
                .creator_banned_from_community(false)
                .subscribed(lemmyCommunityService.getPersonCommunitySubscribeType(creator, comment.getCommunity()))
                .build();
    }
}
