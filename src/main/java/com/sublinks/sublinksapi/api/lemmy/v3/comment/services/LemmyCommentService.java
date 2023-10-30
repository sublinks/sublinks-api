package com.sublinks.sublinksapi.api.lemmy.v3.comment.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers.LemmyCommentMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.mappers.LemmyPostMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.comment.CommentAggregate;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyCommentService {
    private final LemmyCommentMapper lemmyCommentMapper;
    private final LemmyPersonMapper lemmyPersonMapper;
    private final LemmyCommunityMapper lemmyCommunityMapper;
    private final LemmyPostMapper lemmyPostMapper;
    private final LemmyCommunityService lemmyCommunityService;
    private final LocalInstanceContext localInstanceContext;

    public String generateActivityPubId(final Comment comment) {

        String domain = localInstanceContext.instance().getDomain();
        return String.format("%s/comment/%d", domain, comment.getId());
    }

    public CommentView createCommentView(final Comment comment, final Person person) {

        final com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment lemmyComment = lemmyCommentMapper.commentToComment(comment);

        final Person creator = comment.getPerson();
        final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person lemmyCreator = lemmyPersonMapper.personToPerson(creator);

        final Community community = comment.getCommunity();
        final com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community lemmyCommunity = lemmyCommunityMapper.communityToLemmyCommunity(community);

        final Post post = comment.getPost();
        final com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post lemmyPost = lemmyPostMapper.postToPost(post);

        CommentAggregate commentAggregate = comment.getCommentAggregate();
        if (comment.getCommentAggregate() == null) {
            commentAggregate = CommentAggregate.builder().build();
        }

        final CommentAggregates lemmyCommentAggregates = lemmyCommentMapper.toCommentAggregates(commentAggregate);

        final SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(person, community);

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
