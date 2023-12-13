package com.sublinks.sublinksapi.api.lemmy.v3.post.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.services.PostLikeService;
import com.sublinks.sublinksapi.post.services.PostSaveService;
import com.sublinks.sublinksapi.post.services.PostService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPostService {

  private final PostService postService;
  private final PostSaveService postSaveService;
  private final PostLikeService postLikeService;
  private final ConversionService conversionService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  public PostView postViewFromPost(final Post post) {
    return postViewBuilder(post).saved(false).read(false).creator_blocked(false).my_vote(0)
        .unread_comments(0).build();
  }

  public PostView postViewFromPost(final Post post,
      final com.sublinks.sublinksapi.person.dto.Person person) {

    Optional<PostLike> postLike = postLikeService.getPostLike(post, person);
    int vote = 0;
    if (postLike.isPresent()) {
      vote = postLike.get().getScore();
    }

    return postViewBuilder(post).saved(postSaveService.isPostSaved(post, person)).read(false)
        .creator_blocked(false).my_vote(vote).unread_comments(0).build();
  }

  private PostView.PostViewBuilder postViewBuilder(final Post post) {

    final com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post lemmyPost = conversionService.convert(
        post, com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post.class);
    final Person creator = postService.getPostCreator(post);
    final com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person lemmyCreator = conversionService.convert(
        creator, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class);
    final Community community = conversionService.convert(post.getCommunity(), Community.class);

    final PostAggregates postAggregates = conversionService.convert(post.getPostAggregate(),
        PostAggregates.class);

    final boolean creatorBannedFromCommunity = linkPersonCommunityService.hasLink(creator,
        post.getCommunity(), LinkPersonCommunityType.banned);

    return PostView.builder().post(lemmyPost).creator(lemmyCreator).community(community)
        .counts(postAggregates).creator_banned_from_community(creatorBannedFromCommunity);
  }
}
