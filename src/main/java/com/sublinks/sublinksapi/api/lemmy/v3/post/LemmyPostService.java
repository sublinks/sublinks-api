package com.sublinks.sublinksapi.api.lemmy.v3.post;

import com.sublinks.sublinksapi.api.lemmy.v3.community.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates.PostAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.LemmyPersonMapper;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.post.PostService;
import org.springframework.stereotype.Service;

@Service
public class LemmyPostService {

    private final LemmyPostMapper lemmyPostMapper;
    private final LemmyCommunityMapper lemmyCommunityMapper;
    private final LemmyPersonMapper lemmyPersonMapper;
    private final PostService postService;

    public LemmyPostService(LemmyPostMapper lemmyPostMapper, LemmyCommunityMapper lemmyCommunityMapper, LemmyPersonMapper lemmyPersonMapper, PostService postService) {
        this.lemmyPostMapper = lemmyPostMapper;
        this.lemmyCommunityMapper = lemmyCommunityMapper;
        this.lemmyPersonMapper = lemmyPersonMapper;
        this.postService = postService;
    }

    public PostView postViewFromPost(Post post) {
        com.sublinks.sublinksapi.api.lemmy.v3.models.Post lemmyPost = lemmyPostMapper.postToPost(post);
        com.sublinks.sublinksapi.api.lemmy.v3.models.Person creator = lemmyPersonMapper.personToPerson(
                postService.getPostCreator(post)
        );
        Community community = lemmyCommunityMapper.communityToLemmyCommunity(post.getCommunity());
        PostAggregates postAggregates = lemmyPostMapper.postAggregatesToPostAggregates(
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
                .my_vote(1)
                .unread_comments(0)
                .build();
    }

    public PostView postViewFromPost(Post post, Person person) {
        com.sublinks.sublinksapi.api.lemmy.v3.models.Post lemmyPost = lemmyPostMapper.postToPost(post);
        com.sublinks.sublinksapi.api.lemmy.v3.models.Person creator = lemmyPersonMapper.personToPerson(
                postService.getPostCreator(post)
        );
        Community community = lemmyCommunityMapper.communityToLemmyCommunity(post.getCommunity());
        PostAggregates postAggregates = lemmyPostMapper.postAggregatesToPostAggregates(
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
                .my_vote(1)
                .unread_comments(0)
                .build();
    }
}
