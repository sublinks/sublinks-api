package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.mappers.LemmyListingTypeMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.mappers.LemmySortTypeMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.mappers.GetPostResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.mappers.PostViewMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.CreatePostLike;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPosts;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteMetadataResponse;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.community.CommunityRepository;
import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.post.PostLikeService;
import com.sublinks.sublinksapi.post.PostRepository;
import com.sublinks.sublinksapi.post.PostService;
import com.sublinks.sublinksapi.post.SearchCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/post")
public class PostController {
    private final LemmyCommunityService lemmyCommunityService;
    private final LemmyPostService lemmyPostService;
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final PostViewMapper postViewMapper;
    private final GetPostResponseMapper getPostResponseMapper;
    private final LemmySortTypeMapper lemmySortTypeMapper;
    private final LemmyListingTypeMapper lemmyListingTypeMapper;

    @GetMapping
    GetPostResponse show(@Valid final GetPost getPostForm, final JwtPerson person) {

        final Post post = postRepository.findById((long) getPostForm.id()).get();
        final Community community = post.getCommunity();

        PostView postView;
        if (person != null) {
            postView = lemmyPostService.postViewFromPost(post, (Person) person.getPrincipal());
        } else {
            postView = lemmyPostService.postViewFromPost(post);
        }
        final CommunityView communityView = lemmyCommunityService.communityViewFromCommunity(community);
        final List<CommunityModeratorView> moderators = lemmyCommunityService.communityModeratorViewList(community);
        final List<PostView> crossPosts = new ArrayList<>();//@todo cross post
        return getPostResponseMapper.map(postView, communityView, moderators, crossPosts);
    }

    @PostMapping("mark_as_read")
    PostResponse markAsRead() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("list")
    @Transactional(readOnly = true)
    public GetPostsResponse index(@Valid final GetPosts getPostsForm, final JwtPerson principal) {

        Person person = null;
        if (principal != null) {
            person = (Person) principal.getPrincipal();
        }

        final List<Long> communityIds = new ArrayList<>();
        Community community = null;
        if (getPostsForm.community_name() != null || getPostsForm.community_id() != null) {
            Long communityId = getPostsForm.community_id() == null ? null : (long) getPostsForm.community_id();
            community = communityRepository.findCommunityByIdOrTitleSlug(
                    communityId,
                    getPostsForm.community_name()
            );
            communityIds.add(community.getId());
        }

        if (person != null) {
            switch (getPostsForm.type_()) {
                case Subscribed -> {
                    final Set<LinkPersonCommunity> personCommunities = person.getLinkPersonCommunity();
                    for (LinkPersonCommunity l : personCommunities) {
                        if (l.getLinkType() == LinkPersonCommunityType.follower) {
                            communityIds.add(l.getCommunity().getId());
                        }
                    }
                }
                case Local -> {
                    for (Community c : communityRepository.findAll()) { // @todo find local
                        communityIds.add(c.getId());
                    }
                }
                case All, ModeratorView -> {
                    // @todo only non deleted communities
                    // fall through for now
                }
            }
        }

        SortType sortType = null;
        if (getPostsForm.sort() != null) {
            sortType = lemmySortTypeMapper.map(getPostsForm.sort());
        }

        ListingType listingType = null;
        if (getPostsForm.type_() != null) {
            listingType = lemmyListingTypeMapper.map(getPostsForm.type_());
        }

        final SearchCriteria searchCriteria = SearchCriteria.builder()
                .page(1)
                .listingType(listingType)
                .perPage(20)
                .isSavedOnly(getPostsForm.saved_only() != null && getPostsForm.saved_only())
                .isDislikedOnly(getPostsForm.disliked_only() != null && getPostsForm.disliked_only())
                .sortType(sortType)
                .person(person)
                .communityIds(communityIds)
                .build();

        final Collection<Post> posts = postRepository.allPostsBySearchCriteria(searchCriteria);
        final Collection<PostView> postViewCollection = new HashSet<>();
        for (Post post : posts) {
            final Person creator = postService.getPostCreator(post);
            final PostView postView = postViewMapper.map(
                    post,
                    post.getCommunity(),
                    lemmyCommunityService.getPersonCommunitySubscribeType(person, post.getCommunity()),
                    creator,
                    post.getPostLikes().iterator().next().getScore()
            );
            postViewCollection.add(postView);
        }

        return GetPostsResponse.builder()
                .posts(postViewCollection)
                .build();
    }

    @PostMapping("like")
    PostResponse like(@Valid @RequestBody CreatePostLike createPostLikeForm, JwtPerson jwtPerson) {

        final Person person = (Person) jwtPerson.getPrincipal();
        final Optional<Post> post = postRepository.findById(createPostLikeForm.post_id());
        if (createPostLikeForm.score() == 1) {
            postLikeService.updateOrCreatePostLikeLike(post.get(), person);
        } else if (createPostLikeForm.score() == -1) {
            postLikeService.updateOrCreatePostLikeDislike(post.get(), person);
        } else {
            postLikeService.updateOrCreatePostLikeNeutral(post.get(), person);
        }

        final SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(person, post.get().getCommunity());

        return PostResponse.builder()
                .post_view(postViewMapper.map(
                        post.get(),
                        post.get().getCommunity(),
                        subscribedType,
                        person,
                        createPostLikeForm.score()
                ))
                .build();
    }

    @PostMapping("save")
    PostResponse saveForLater() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report")
    PostReportResponse report() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("site_metadata")
    GetSiteMetadataResponse siteMetadata() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
