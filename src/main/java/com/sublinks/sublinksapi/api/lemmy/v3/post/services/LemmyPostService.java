package com.sublinks.sublinksapi.api.lemmy.v3.post.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.mappers.LemmyPostMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPostService {
    private final LemmyPostMapper lemmyPostMapper;
    private final LemmyCommunityMapper lemmyCommunityMapper;
    private final LemmyPersonMapper lemmyPersonMapper;
    private final PostService postService;

    public PostView postViewFromPost(final Post post) {

        final com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post lemmyPost = lemmyPostMapper.postToPost(post);
        final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person creator = lemmyPersonMapper.personToPerson(
                postService.getPostCreator(post)
        );
        final Community community = lemmyCommunityMapper.communityToLemmyCommunity(post.getCommunity());
        final PostAggregates postAggregates = lemmyPostMapper.postAggregatesToPostAggregates(
                post.getPostAggregates()
        );
        return PostView.builder()
                .post(lemmyPost)
                .creator(creator)
                .community(community)
                .counts(postAggregates)
                .creator_banned_from_community(false)
                .saved(false)
                .read(false)
                .creator_blocked(false)
                .my_vote(0)
                .unread_comments(0)
                .build();
    }

    public PostView postViewFromPost(final Post post, final Person person) {

        final com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post lemmyPost = lemmyPostMapper.postToPost(post);
        final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person creator = lemmyPersonMapper.personToPerson(
                postService.getPostCreator(post)
        );
        final Community community = lemmyCommunityMapper.communityToLemmyCommunity(post.getCommunity());
        final PostAggregates postAggregates = lemmyPostMapper.postAggregatesToPostAggregates(
                post.getPostAggregates()
        );
        return PostView.builder()
                .post(lemmyPost)
                .creator(creator)
                .community(community)
                .counts(postAggregates)
                .creator_banned_from_community(false)
                .saved(false)
                .read(false)
                .creator_blocked(false)
                .my_vote(post.getPostLikes().iterator().next().getScore())
                .unread_comments(0)
                .build();
    }
}
