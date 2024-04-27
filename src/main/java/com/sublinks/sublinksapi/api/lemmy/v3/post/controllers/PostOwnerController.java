package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.CreatePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.DeletePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.EditPost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPostTypes;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostReport;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostReportService;
import com.sublinks.sublinksapi.post.services.PostService;
import com.sublinks.sublinksapi.shared.RemovedState;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterBlockedException;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterReportException;
import com.sublinks.sublinksapi.slurfilter.services.SlurFilterService;
import com.sublinks.sublinksapi.utils.SiteMetadataUtil;
import com.sublinks.sublinksapi.utils.SlugUtil;
import com.sublinks.sublinksapi.utils.UrlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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
public class PostOwnerController extends AbstractLemmyApiController {

  private final LocalInstanceContext localInstanceContext;
  private final RoleAuthorizingService roleAuthorizingService;
  private final PostRepository postRepository;
  private final LemmyPostService lemmyPostService;
  private final PostService postService;
  private final LanguageRepository languageRepository;
  private final CommunityRepository communityRepository;
  private final SlugUtil slugUtil;
  private final PersonService personService;
  private final SiteMetadataUtil siteMetadataUtil;
  private final UrlUtil urlUtil;
  private final SlurFilterService slurFilterService;
  private final PostReportService postReportService;

  @Operation(summary = "Create a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Community Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping
  @Transactional
  public PostResponse create(@Valid @RequestBody final CreatePost createPostForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.isPermitted(person, RolePermissionPostTypes.CREATE_POST,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Community community = communityRepository.findById((long) createPostForm.community_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    // Language
    Optional<Language> language;
    if (createPostForm.language_id() != null) {
      language = languageRepository.findById((long) createPostForm.language_id());
    } else {
      language = personService.getPersonDefaultPostLanguage(person, community);
    }

    if (language.isEmpty()) {
      language = Optional.ofNullable(languageRepository.findLanguageByCode("und"));
    }

    String url = createPostForm.url();
    SiteMetadataUtil.SiteMetadata metadata = null;
    if (createPostForm.url() != null) {
      url = urlUtil.normalizeUrl(createPostForm.url());
      urlUtil.checkUrlProtocol(url);
      metadata = siteMetadataUtil.fetchSiteMetadata(url);
    }

    final Post.PostBuilder postBuilder = Post.builder()
        .instance(localInstanceContext.instance())
        .community(community)
        .language(language.orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found")))
        .title(createPostForm.name())
        .removedState(RemovedState.NOT_REMOVED)
        .titleSlug(slugUtil.uniqueSlug(createPostForm.name()))
        .postBody(createPostForm.body())
        .isNsfw((createPostForm.nsfw() != null && createPostForm.nsfw()));

    boolean shouldReport = false;

    try {
      postBuilder.postBody(slurFilterService.censorText(createPostForm.body()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      postBuilder.postBody(createPostForm.body());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_blocked_by_slur_filter");
    }

    try {
      postBuilder.title(slurFilterService.censorText(createPostForm.name()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      postBuilder.title(createPostForm.name());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_blocked_by_slur_filter");
    }

    if (url != null) {
      postBuilder.linkUrl(url);
      if (metadata != null) {
        postBuilder.linkTitle(metadata.title())
            .linkDescription(metadata.description())
            .linkVideoUrl(metadata.videoUrl())
            .linkThumbnailUrl(metadata.imageUrl());
      }
    }

    final Post post = postBuilder.build();

    postService.createPost(post, person);

    if (shouldReport) {
      postReportService.createPostReport(PostReport.builder()
          .post(post)
          .creator(person)
          .reason("AUTOMATED: Post creation triggered a slur filter")
          .originalBody(post.getPostBody() == null ? "" : post.getPostBody())
          .originalTitle(post.getTitle() == null ? "" : post.getTitle())
          .originalUrl(post.getLinkUrl() == null ? "" : post.getLinkUrl())
          .build());
    }

    return PostResponse.builder()
        .post_view(lemmyPostService.postViewFromPost(post, person))
        .build();
  }

  @Operation(summary = "Edit a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PutMapping
  PostResponse update(@Valid @RequestBody EditPost editPostForm, JwtPerson principal) {

    final Post post = postRepository.findById((long) editPostForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.isPermitted(person, RolePermissionPostTypes.UPDATE_POST,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    post.setTitle(editPostForm.name());
    post.setPostBody(editPostForm.body());
    post.setNsfw((editPostForm.nsfw() != null && editPostForm.nsfw()));

    Optional<Language> language;
    if (editPostForm.language_id() != null) {
      language = languageRepository.findById((long) editPostForm.language_id());
    } else {
      language = personService.getPersonDefaultPostLanguage(person, post.getCommunity());
    }

    if (language.isEmpty()) {
      language = Optional.ofNullable(languageRepository.findLanguageByCode("und"));
    }

    post.setLanguage(language.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found")));

    post.setLinkUrl(editPostForm.url());

    boolean shouldReport = false;

    try {
      post.setPostBody(slurFilterService.censorText(editPostForm.body()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      post.setPostBody(editPostForm.body());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_blocked");
    }

    try {
      post.setTitle(slurFilterService.censorText(editPostForm.name()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      post.setTitle(editPostForm.name());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_blocked");
    }

    postService.updatePost(post);

    if (shouldReport) {
      postReportService.createPostReport(PostReport.builder()
          .post(post)
          .creator(person)
          .reason("AUTOMATED: Post update triggered a slur filter")
          .originalBody(post.getPostBody() == null ? "" : post.getPostBody())
          .originalTitle(post.getTitle() == null ? "" : post.getTitle())
          .originalUrl(post.getLinkUrl() == null ? "" : post.getLinkUrl())
          .build());
    }

    return PostResponse.builder()
        .post_view(lemmyPostService.postViewFromPost(post, person))
        .build();
  }

  @Operation(summary = "Delete a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("delete")
  PostResponse delete(@Valid @RequestBody DeletePost deletePostForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.isPermitted(person, RolePermissionPostTypes.DELETE_POST,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Post post = postRepository.findById((long) deletePostForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    post.setDeleted(deletePostForm.deleted());

    postService.softDeletePost(post);

    return PostResponse.builder()
        .post_view(lemmyPostService.postViewFromPost(post, person))
        .build();
  }
}
