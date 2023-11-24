package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.CreatePostLike;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.CreatePostReport;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPosts;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.MarkPostAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.SavePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
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
import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostLikeService;
import com.sublinks.sublinksapi.post.services.PostReadService;
import com.sublinks.sublinksapi.post.services.PostReportService;
import com.sublinks.sublinksapi.post.services.PostSaveService;
import com.sublinks.sublinksapi.utils.SiteMetadataUtil;
import com.sublinks.sublinksapi.utils.UrlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/api/v3/post")
@Tag(name = "Post")
public class PostController extends AbstractLemmyApiController {

  private final LemmyCommunityService lemmyCommunityService;
  private final LemmyPostService lemmyPostService;
  private final PostLikeService postLikeService;
  private final PostSaveService postSaveService;
  private final CommunityRepository communityRepository;
  private final PostRepository postRepository;
  private final UrlUtil urlUtil;
  private final PostReadService postReadService;
  private final ConversionService conversionService;
  private final SiteMetadataUtil siteMetadataUtil;
  private final PostReportService postReportService;
  private final LemmyPostReportService lemmyPostReportService;


  @Operation(summary = "Get / fetch a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetPostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @GetMapping
  GetPostResponse show(@Valid final GetPost getPostForm, final JwtPerson principal) {

    final Post post = postRepository.findById((long) getPostForm.id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    final Community community = post.getCommunity();

    Optional<Person> person = getOptionalPerson(principal);

    PostView postView;
    final CommunityView communityView;
    if (person.isPresent()) {
      communityView = lemmyCommunityService.communityViewFromCommunity(community, person.get());
      postView = lemmyPostService.postViewFromPost(post, person.get());
      postReadService.markPostReadByPerson(post, person.get());
    } else {
      communityView = lemmyCommunityService.communityViewFromCommunity(community);
      postView = lemmyPostService.postViewFromPost(post);
    }
    final List<CommunityModeratorView> moderators = lemmyCommunityService.communityModeratorViewList(
        community);
    Set<PostView> crossPosts = new LinkedHashSet<>();
    if (post.getCrossPost() != null && post.getCrossPost().getPosts() != null) {
      for (Post crossPostPost : post.getCrossPost().getPosts()) {
        if (post.equals(crossPostPost)) {
          continue;
        }
        if (person.isPresent()) {
          crossPosts.add(lemmyPostService.postViewFromPost(crossPostPost, person.get()));
        } else {
          crossPosts.add(lemmyPostService.postViewFromPost(crossPostPost));
        }
      }
    }

    return GetPostResponse.builder().post_view(postView).community_view(communityView)
        .moderators(moderators).cross_posts(crossPosts).build();
  }

  @Operation(summary = "Mark a post as read.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("mark_as_read")
  PostResponse markAsRead(@Valid @RequestBody final MarkPostAsRead markPostAsReadForm,
      final JwtPerson principal) {

    final Post post = postRepository.findById((long) markPostAsReadForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    final Person person = getPersonOrThrowBadRequest(principal);
    postReadService.markPostReadByPerson(post, person);
    return PostResponse.builder().post_view(lemmyPostService.postViewFromPost(post, person))
        .build();
  }

  @Operation(summary = "Get / fetch posts, with various filters.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetPostsResponse.class))})})
  @GetMapping("list")
  @Transactional(readOnly = true)
  public GetPostsResponse index(@Valid final GetPosts getPostsForm, final JwtPerson principal) {

    final Optional<Person> person = getOptionalPerson(principal);

    final List<Long> communityIds = new ArrayList<>();
    Community community = null;
    if (getPostsForm.community_name() != null || getPostsForm.community_id() != null) {
      Long communityId =
          getPostsForm.community_id() == null ? null : (long) getPostsForm.community_id();
      community = communityRepository.findCommunityByIdOrTitleSlug(communityId,
          getPostsForm.community_name());
      communityIds.add(community.getId());
    }

    if (person.isPresent() && getPostsForm.type_() != null) {
      switch (getPostsForm.type_()) {
        case Subscribed -> {
          final Set<LinkPersonCommunity> personCommunities = person.get().getLinkPersonCommunity();
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

    SortType sortType = null; // @todo set to site default
    if (getPostsForm.sort() != null) {
      sortType = conversionService.convert(getPostsForm.sort(), SortType.class);
    }
    ListingType listingType = null; // @todo set to site default
    if (getPostsForm.type_() != null) {
      listingType = conversionService.convert(getPostsForm.type_(), ListingType.class);
    }

    final PostSearchCriteria postSearchCriteria = PostSearchCriteria.builder().page(1)
        .listingType(listingType).perPage(20)
        .isSavedOnly(getPostsForm.saved_only() != null && getPostsForm.saved_only())
        .isDislikedOnly(getPostsForm.disliked_only() != null && getPostsForm.disliked_only())
        .sortType(sortType).person(person.orElse(null)).communityIds(communityIds).build();

    final Collection<Post> posts = postRepository.allPostsBySearchCriteria(postSearchCriteria);
    final Collection<PostView> postViewCollection = new LinkedHashSet<>();
    for (Post post : posts) {
      if (person.isPresent()) {
        postViewCollection.add(lemmyPostService.postViewFromPost(post, person.get()));
      } else {
        postViewCollection.add(lemmyPostService.postViewFromPost(post));
      }
    }

    return GetPostsResponse.builder().posts(postViewCollection).build();
  }

  @Operation(summary = "Like / vote on a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("like")
  PostResponse like(@Valid @RequestBody CreatePostLike createPostLikeForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    final Post post = postRepository.findById(createPostLikeForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    if (createPostLikeForm.score() == 1) {
      postLikeService.updateOrCreatePostLikeLike(post, person);
    } else if (createPostLikeForm.score() == -1) {
      postLikeService.updateOrCreatePostLikeDislike(post, person);
    } else {
      postLikeService.updateOrCreatePostLikeNeutral(post, person);
    }

    return PostResponse.builder().post_view(lemmyPostService.postViewFromPost(post, person))
        .build();
  }

  @Operation(summary = "Save a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PutMapping("save")
  public PostResponse saveForLater(@Valid @RequestBody SavePost savePostForm, JwtPerson jwtPerson) {

    final Post post = postRepository.findById((long) savePostForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    final Person person = getPersonOrThrowUnauthorized(jwtPerson);
    if (savePostForm.save()) {
      postSaveService.createPostSave(post, person);
    } else {
      postSaveService.deletePostSave(post, person);
    }
    return PostResponse.builder().post_view(lemmyPostService.postViewFromPost(post, person))
        .build();
  }

  @Operation(summary = "Report a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostReportResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))})})
  @PostMapping("report")
  PostReportResponse report(@Valid @RequestBody final CreatePostReport createPostReportForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Post post = postRepository.findById((long) createPostReportForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_not_found"));

    final PostReport postReport = PostReport.builder()
        .post(post)
        .creator(person)
        .reason(createPostReportForm.reason())
        .originalBody(post.getPostBody())
        .originalTitle(post.getTitle())
        .originalUrl(post.getLinkUrl())
        .build();

    postReportService.createPostReport(postReport);

    return PostReportResponse.builder()
        .post_report_view(lemmyPostReportService.postReportViewFromPost(postReport, person))
        .build();

  }

  @Operation(summary = "Fetch metadata for any given site.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetSiteMetadataResponse.class))})})
  @GetMapping("site_metadata")
  public GetSiteMetadataResponse siteMetadata(@Valid GetSiteMetadata getSiteMetadataForm) {

    String normalizedUrl = urlUtil.normalizeUrl(getSiteMetadataForm.url());
    SiteMetadataUtil.SiteMetadata siteMetadata = siteMetadataUtil.fetchSiteMetadata(normalizedUrl);

    return GetSiteMetadataResponse.builder().metadata(
            SiteMetadata.builder().title(siteMetadata.title()).description(siteMetadata.description())
                .image(siteMetadata.imageUrl()).embed_video_url(siteMetadata.videoUrl()).build())
        .build();
  }
}
