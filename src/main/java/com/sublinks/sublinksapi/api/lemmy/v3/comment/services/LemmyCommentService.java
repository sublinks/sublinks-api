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
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import org.springframework.stereotype.Service;

@Service
public class LemmyCommentService {
    private final LemmyCommentMapper lemmyCommentMapper;
    private final LemmyPersonMapper lemmyPersonMapper;
    private final LemmyCommunityMapper lemmyCommunityMapper;
    private final LemmyPostMapper lemmyPostMapper;
    private final LemmyCommunityService lemmyCommunityService;

    public LemmyCommentService(LemmyCommentMapper lemmyCommentMapper, LemmyPersonMapper lemmyPersonMapper, LemmyCommunityMapper lemmyCommunityMapper, LemmyPostMapper lemmyPostMapper, LemmyCommunityService lemmyCommunityService) {
        this.lemmyCommentMapper = lemmyCommentMapper;
        this.lemmyPersonMapper = lemmyPersonMapper;
        this.lemmyCommunityMapper = lemmyCommunityMapper;
        this.lemmyPostMapper = lemmyPostMapper;
        this.lemmyCommunityService = lemmyCommunityService;
    }

    public CommentView createCommentView(Comment comment, Person person) {
        com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment lemmyComment = lemmyCommentMapper.commentToComment(comment);

        Person creator = comment.getPerson();
        com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person lemmyCreator = lemmyPersonMapper.personToPerson(creator);

        Community community = comment.getCommunity();
        com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community lemmyCommunity = lemmyCommunityMapper.communityToLemmyCommunity(community);

        Post post = comment.getPost();
        com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post lemmyPost = lemmyPostMapper.postToPost(post);

        CommentAggregate commentAggregate = comment.getCommentAggregate();
        if (comment.getCommentAggregate() == null) {
            commentAggregate = CommentAggregate.builder().build();
        }

        CommentAggregates lemmyCommentAggregates = lemmyCommentMapper.toCommentAggregates(commentAggregate);

        SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(person, community);

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
