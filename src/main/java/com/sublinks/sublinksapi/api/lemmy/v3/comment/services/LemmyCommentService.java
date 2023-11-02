package com.sublinks.sublinksapi.api.lemmy.v3.comment.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyCommentService {
    private final LemmyPersonMapper lemmyPersonMapper;
    private final LemmyCommunityMapper lemmyCommunityMapper;
    private final LemmyCommunityService lemmyCommunityService;
    private final LocalInstanceContext localInstanceContext;
    private final ConversionService conversionService;

    public String generateActivityPubId(final com.sublinks.sublinksapi.comment.dto.Comment comment) {

        String domain = localInstanceContext.instance().getDomain();
        return String.format("%s/comment/%d", domain, comment.getId());
    }

    public CommentView createCommentView(final com.sublinks.sublinksapi.comment.dto.Comment comment, final com.sublinks.sublinksapi.person.dto.Person person) {

        final Comment lemmyComment = conversionService.convert(comment, Comment.class);

        final com.sublinks.sublinksapi.person.dto.Person creator = comment.getPerson();
        final Person lemmyCreator = lemmyPersonMapper.personToPerson(creator);

        final Community lemmyCommunity = lemmyCommunityMapper.communityToLemmyCommunity(comment.getCommunity());
        final Post lemmyPost = conversionService.convert(comment.getPost(), Post.class);

        CommentAggregate commentAggregate = comment.getCommentAggregate();
        if (comment.getCommentAggregate() == null) {
            commentAggregate = CommentAggregate.builder().build();
        }

        final CommentAggregates lemmyCommentAggregates = conversionService.convert(commentAggregate, CommentAggregates.class);

        final SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(person, comment.getCommunity());

        return CommentView.builder()
                .comment(lemmyComment)
                .creator(lemmyCreator)
                .community(lemmyCommunity)
                .post(lemmyPost)
                .counts(lemmyCommentAggregates)
                .subscribed(subscribedType) // @todo fix this
                .creator_banned_from_community(false) // @todo creator checks
                .creator_blocked(false)
                .saved(false)// @todo check if saved
                .my_vote(1) // @todo user vote
                .build();
    }
}
