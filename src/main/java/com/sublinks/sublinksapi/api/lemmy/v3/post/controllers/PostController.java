package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.mappers.LemmyListingTypeMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.mappers.LemmySortTypeMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.mappers.GetPostResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.CreatePostLike;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPosts;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.MarkPostAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.SavePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.api.lemmy.v3.post.utils.Url;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteMetadata;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteMetadataResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteMetadata;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostLikeService;
import com.sublinks.sublinksapi.post.services.PostReadService;
import com.sublinks.sublinksapi.post.services.PostSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/api/v3/post")
public class PostController {
    private final LemmyCommunityService lemmyCommunityService;
    private final LemmyPostService lemmyPostService;
    private final PostLikeService postLikeService;
    private final PostSaveService postSaveService;
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final GetPostResponseMapper getPostResponseMapper;
    private final LemmySortTypeMapper lemmySortTypeMapper;
    private final LemmyListingTypeMapper lemmyListingTypeMapper;
    private final Url url;
    private final PostReadService postReadService;

    @GetMapping
    GetPostResponse show(@Valid final GetPost getPostForm, final JwtPerson person) {

        Post post = postRepository.findById((long) getPostForm.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        final Community community = post.getCommunity();

        PostView postView;
        if (person != null) {
            postView = lemmyPostService.postViewFromPost(post, (Person) person.getPrincipal());
            postReadService.markPostReadByPerson(post, (Person) person.getPrincipal());
        } else {
            postView = lemmyPostService.postViewFromPost(post);
        }
        final CommunityView communityView = lemmyCommunityService.communityViewFromCommunity(community);
        final List<CommunityModeratorView> moderators = lemmyCommunityService.communityModeratorViewList(community);
        final List<PostView> crossPosts = new ArrayList<>();//@todo cross post
        return getPostResponseMapper.map(postView, communityView, moderators, crossPosts);
    }

    @PostMapping("mark_as_read")
    PostResponse markAsRead(@Valid @RequestBody final MarkPostAsRead markPostAsReadForm, final JwtPerson principal) {

        Optional<Post> post = postRepository.findById((long) markPostAsReadForm.post_id());
        if (post.isEmpty() || principal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        final Person person = (Person) principal.getPrincipal();
        postReadService.markPostReadByPerson(post.get(), person);
        return PostResponse.builder()
                .post_view(lemmyPostService.postViewFromPost(post.get(), person))
                .build();
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

        final PostSearchCriteria postSearchCriteria = PostSearchCriteria.builder()
                .page(1)
                .listingType(listingType)
                .perPage(20)
                .isSavedOnly(getPostsForm.saved_only() != null && getPostsForm.saved_only())
                .isDislikedOnly(getPostsForm.disliked_only() != null && getPostsForm.disliked_only())
                .sortType(sortType)
                .person(person)
                .communityIds(communityIds)
                .build();

        final Collection<Post> posts = postRepository.allPostsBySearchCriteria(postSearchCriteria);
        final Collection<PostView> postViewCollection = new HashSet<>();
        for (Post post : posts) {
            final PostView postView = lemmyPostService.postViewFromPost(post, person);
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

        return PostResponse.builder()
                .post_view(lemmyPostService.postViewFromPost(post.get(), person))
                .build();
    }

    @PutMapping("save")
    public PostResponse saveForLater(@Valid @RequestBody SavePost savePostForm, JwtPerson jwtPerson) {

        Optional<Post> post = postRepository.findById((long) savePostForm.post_id());
        if (post.isEmpty() || jwtPerson == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        final Person person = (Person) jwtPerson.getPrincipal();
        if (savePostForm.save()) {
            postSaveService.createPostSave(post.get(), person);
        } else {
            postSaveService.deletePostSave(post.get(), person);
        }
        return PostResponse.builder()
                .post_view(lemmyPostService.postViewFromPost(post.get(), person))
                .build();
    }

    @PostMapping("report")
    PostReportResponse report() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("site_metadata")
    public GetSiteMetadataResponse siteMetadata(@Valid GetSiteMetadata getSiteMetadataForm) {

        String normalizedUrl = null;
        try {
            normalizedUrl = url.normalizeUrl(getSiteMetadataForm.url());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Document doc = null;
        try {
            doc = Jsoup.connect(normalizedUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements metaOgTitle = doc.select("meta[property=og:title]");
        Elements metaOgDescription = doc.select("meta[property=og:description]");
        Elements metaOgImage = doc.select("meta[property=og:image]");
        Elements metaOgVideo = doc.select("meta[property=og:video:url]");

        String title = doc.title();
        if (!metaOgTitle.isEmpty()) {
            title = metaOgTitle.first().attr("content");
        }
        String description = null;
        if (!metaOgDescription.isEmpty()) {
            description = metaOgDescription.first().attr("content");
        }
        String image = null;
        if (!metaOgImage.isEmpty()) {
            image = metaOgImage.first().attr("content");
        }
        String video = null;
        if (!metaOgVideo.isEmpty()) {
            video = metaOgVideo.first().attr("content");
        }

        return GetSiteMetadataResponse.builder()
                .metadata(SiteMetadata.builder()
                        .title(title)
                        .description(description)
                        .image(image)
                        .embed_video_url(video)
                        .build())
                .build();
    }
}
