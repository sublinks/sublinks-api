package com.sublinks.sublinksapi.api.lemmy.v3.post.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.services.PostLikeService;
import com.sublinks.sublinksapi.post.services.PostSaveService;
import com.sublinks.sublinksapi.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LemmyPostService {
    private final PostService postService;
    private final PostSaveService postSaveService;
    private final PostLikeService postLikeService;
    private final ConversionService conversionService;

    public PostView postViewFromPost(final Post post) {

        return postViewBuilder(post)
                .creator_banned_from_community(false)
                .saved(false)
                .read(false)
                .creator_blocked(false)
                .my_vote(0)
                .unread_comments(0)
                .build();
    }

    public PostView postViewFromPost(final Post post, final com.sublinks.sublinksapi.person.dto.Person person) {

        Optional<PostLike> postLike = postLikeService.getPostLike(post, person);
        int vote = 0;
        if (postLike.isPresent()) {
            vote = postLike.get().getScore();
        }
        return postViewBuilder(post)
                .creator_banned_from_community(false)
                .saved(postSaveService.isPostSaved(post, person))
                .read(false)
                .creator_blocked(false)
                .my_vote(vote)
                .unread_comments(0)
                .build();
    }

    private PostView.PostViewBuilder postViewBuilder(final Post post) {

        final com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post lemmyPost = conversionService.convert(
                post, com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post.class
        );
        final Person creator = conversionService.convert(
                postService.getPostCreator(post), Person.class
        );
        final Community community = conversionService.convert(post.getCommunity(), Community.class);

        final PostAggregates postAggregates = conversionService.convert(post.getPostAggregate(), PostAggregates.class);

        return PostView.builder()
                .post(lemmyPost)
                .creator(creator)
                .community(community)
                .counts(postAggregates);
    }
}
