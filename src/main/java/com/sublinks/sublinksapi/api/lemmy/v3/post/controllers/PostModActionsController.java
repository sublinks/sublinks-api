package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModLockPost;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModRemovePost;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.services.ModerationLogService;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.FeaturePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.ListPostReports;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.ListPostReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.ResolvePostReport;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.moderation.dto.ModerationLog;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.models.PostReportSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostReportRepository;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostReportService;
import com.sublinks.sublinksapi.post.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@Transactional
@RequestMapping(path = "/api/v3/post")
@Tag(name = "Post")
public class PostModActionsController extends AbstractLemmyApiController {

  private final PostReportService postReportService;
  private final LemmyPostReportService lemmyPostReportService;
  private final PostReportRepository postReportRepository;
  private final RoleAuthorizingService roleAuthorizingService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final CommunityRepository communityRepository;
  private final PostRepository postRepository;
  private final LemmyPostService lemmyPostService;
  private final PostService postService;
  private final ModerationLogService moderationLogService;

  @Operation(summary = "A moderator remove for a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("remove")
  PostResponse remove(@Valid @RequestBody final ModRemovePost modRemovePostForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Post post = postRepository.findById(modRemovePostForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    final boolean isAdmin = roleAuthorizingService.isAdmin(person);

    if (!isAdmin) {
      final boolean moderatesCommunity =
          linkPersonCommunityService.hasLink(person, post.getCommunity(),
              LinkPersonCommunityType.moderator) || linkPersonCommunityService.hasLink(person,
              post.getCommunity(), LinkPersonCommunityType.owner);

      if (!moderatesCommunity) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_a_moderator");
      }
    }

    post.setRemoved(modRemovePostForm.removed());
    postService.updatePost(post);

    // Create Moderation Log
    ModerationLog moderationLog = ModerationLog.builder()
        .actionType(ModlogActionType.ModRemovePost)
        .removed(modRemovePostForm.removed())
        .entityId(post.getId())
        .postId(post.getId())
        .communityId(post.getCommunity().getId())
        .instance(post.getInstance())
        .moderationPersonId(person.getId())
        .reason(modRemovePostForm.reason())
        .build();
    moderationLogService.createModerationLog(moderationLog);

    return PostResponse.builder().post_view(lemmyPostService.postViewFromPost(post)).build();
  }

  @Operation(summary = "A moderator lock for a post.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))})})
  @PostMapping("lock")
  PostResponse lock(@Valid @RequestBody final ModLockPost modLockPostForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Post post = postRepository.findById(modLockPostForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post_not_found"));

    final boolean isAdmin = roleAuthorizingService.isAdmin(person);

    if (!isAdmin) {
      final boolean moderatesCommunity =
          linkPersonCommunityService.hasLink(person, post.getCommunity(),
              LinkPersonCommunityType.moderator) || linkPersonCommunityService.hasLink(person,
              post.getCommunity(), LinkPersonCommunityType.owner);

      if (!moderatesCommunity) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_a_moderator");
      }
    }

    post.setLocked(modLockPostForm.locked());
    postService.updatePost(post);

    // Create Moderation Log
    ModerationLog moderationLog = ModerationLog.builder()
        .actionType(ModlogActionType.ModLockPost)
        .locked(modLockPostForm.locked())
        .entityId(post.getId())
        .postId(post.getId())
        .communityId(post.getCommunity().getId())
        .instance(post.getInstance())
        .moderationPersonId(person.getId())
        .build();
    moderationLogService.createModerationLog(moderationLog);

    return PostResponse.builder().post_view(lemmyPostService.postViewFromPost(post)).build();
  }

  @Operation(summary = "A moderator feature for a post (Sticky).")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("feature")
  PostResponse feature(@Valid @RequestBody FeaturePost featurePostForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Post post = postRepository.findById((long) featurePostForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    final boolean isAdmin = roleAuthorizingService.isAdmin(person);

    if (!isAdmin) {
      final boolean moderatesCommunity =
          linkPersonCommunityService.hasLink(person, post.getCommunity(),
              LinkPersonCommunityType.moderator) || linkPersonCommunityService.hasLink(person,
              post.getCommunity(), LinkPersonCommunityType.owner);

      if (!moderatesCommunity) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_a_moderator");
      }
    }
    switch (featurePostForm.feature_type()) {
      case Community:
        post.setFeaturedInCommunity(featurePostForm.featured());
        break;
      case Local:
        if (!isAdmin) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_a_admin");
        }
        post.setFeatured(featurePostForm.featured());
        break;
      default:
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_feature_type");
    }

    postService.updatePost(post);

    // Create Moderation Log
    ModerationLog moderationLog = ModerationLog.builder()
        .actionType(ModlogActionType.ModFeaturePost)
        .featured(post.isFeatured())
        .featuredCommunity(post.isFeaturedInCommunity())
        .communityId(post.getCommunity().getId())
        .entityId(post.getId())
        .postId(post.getId())
        .instance(post.getInstance())
        .moderationPersonId(person.getId())
        .build();
    moderationLogService.createModerationLog(moderationLog);

    return PostResponse.builder().post_view(lemmyPostService.postViewFromPost(post)).build();
  }

  @Operation(summary = "Resolve a post report. Only a mod can do this.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostReportResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))})})
  @PutMapping("report/resolve")
  PostReportResponse reportResolve(
      @Valid @RequestBody final ResolvePostReport resolvePostReportForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final boolean isAdmin = roleAuthorizingService.isAdmin(person);

    final PostReport postReport = postReportRepository.findById(
            (long) resolvePostReportForm.report_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!isAdmin) {
      final boolean moderatesCommunity =
          linkPersonCommunityService.hasLink(person, postReport.getPost().getCommunity(),
              LinkPersonCommunityType.moderator) || linkPersonCommunityService.hasLink(person,
              postReport.getPost().getCommunity(), LinkPersonCommunityType.owner);

      if (!moderatesCommunity) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }
    }

    postReport.setResolved(resolvePostReportForm.resolved());
    postReport.setResolver(person);

    postReportService.updatePostReport(postReport);

    return PostReportResponse.builder().post_report_view(
        lemmyPostReportService.postReportViewFromPost(postReport, postReport.getCreator())).build();
  }

  @Operation(summary = "List post reports.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListPostReportsResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))})})
  @GetMapping("report/list")
  ListPostReportsResponse reportList(@Valid final ListPostReports listPostReportsForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final boolean isAdmin = roleAuthorizingService.isAdmin(person);

    final List<PostReport> postReports = new ArrayList<>();

    if (!isAdmin) {
      final List<Community> moderatingCommunities = new ArrayList<>();
      moderatingCommunities.addAll(
          linkPersonCommunityService.getPersonLinkByType(person, LinkPersonCommunityType.owner));

      moderatingCommunities.addAll(linkPersonCommunityService.getPersonLinkByType(person,
          LinkPersonCommunityType.moderator));

      postReports.addAll(postReportRepository.allPostReportsBySearchCriteria(
          PostReportSearchCriteria.builder().unresolvedOnly(
                  listPostReportsForm.unresolved_only() == null
                      || listPostReportsForm.unresolved_only()).perPage(listPostReportsForm.limit())
              .page(listPostReportsForm.page()).community(moderatingCommunities).build()));
    } else {
      postReports.addAll(postReportRepository.allPostReportsBySearchCriteria(
          PostReportSearchCriteria.builder().unresolvedOnly(
                  listPostReportsForm.unresolved_only() != null
                      && listPostReportsForm.unresolved_only()).perPage(listPostReportsForm.limit())
              .page(listPostReportsForm.page()).build()));
    }

    List<PostReportView> postReportView = new ArrayList<>();

    for (PostReport postReport : postReports) {
      postReportView.add(lemmyPostReportService.postReportViewFromPost(postReport, person));
    }

    return ListPostReportsResponse.builder().post_reports(postReportView).build();
  }
}
