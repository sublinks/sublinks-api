package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.DistinguishComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReports;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.RemoveComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ResolveCommentReport;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.services.ModerationLogService;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.PaginationControllerUtils;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommentTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommunityTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionInstanceTypes;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentReportService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.shared.RemovedState;
import io.jsonwebtoken.lang.Collections;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller class for handling comment moderation actions.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/comment")
@Tag(name = "Comment")
public class CommentModActionsController extends AbstractLemmyApiController {

  private final LemmyCommentReportService lemmyCommentReportService;
  private final LemmyCommentService lemmyCommentService;
  private final CommentReportRepository commentReportRepository;
  private final CommentReportService commentReportService;
  private final RolePermissionService rolePermissionService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final CommunityRepository communityRepository;
  private final CommentRepository commentRepository;
  private final CommentService commentService;
  private final ModerationLogService moderationLogService;

  /**
   * Removes a comment as a moderator.
   *
   * @param removeCommentForm The form containing the comment ID, removed state, and reason.
   * @param principal         The authenticated user.
   * @return The updated comment response.
   */
  @Operation(summary = "A moderator remove for a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))}),
      @ApiResponse(responseCode = "404",
          description = "Not Found",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ResponseStatusException.class)))})
  @PostMapping("remove")
  CommentResponse remove(@Valid @RequestBody RemoveComment removeCommentForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    rolePermissionService.isPermitted(person,
        Set.of(RolePermissionCommentTypes.MODERATOR_REMOVE_COMMENT,
            RolePermissionCommentTypes.REMOVE_COMMENT),
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));
    //@todo: check if he is mod if not REMOVE_COMMENT

    final Comment comment = commentRepository.findById((long) removeCommentForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    comment.setRemovedState(
        removeCommentForm.removed() ? RemovedState.REMOVED : RemovedState.NOT_REMOVED);

    commentService.updateComment(comment);

    commentReportService.resolveAllReportsByComment(comment, person);

    // Create Moderation Log
    ModerationLog moderationLog = ModerationLog.builder()
        .actionType(ModlogActionType.ModRemoveComment)
        .removed(removeCommentForm.removed())
        .entityId(comment.getId())
        .commentId(comment.getId())
        .postId(comment.getPost()
            .getId())
        .communityId(comment.getCommunity()
            .getId())
        .instance(comment.getPost()
            .getInstance())
        .otherPersonId(comment.getPerson()
            .getId())
        .moderationPersonId(person.getId())
        .build();
    moderationLogService.createModerationLog(moderationLog);

    return CommentResponse.builder()
        .comment_view(lemmyCommentService.createCommentView(comment, comment.getPerson()))
        .build();
  }

  /**
   * Distinguishes a comment (speak as moderator).
   *
   * @param distinguishCommentForm The form containing the comment ID and the distinguished state.
   * @param principal              The authenticated user.
   * @return The updated comment response.
   */
  @Operation(summary = "Distinguishes a comment (speak as moderator).")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))}),
      @ApiResponse(responseCode = "404",
          description = "Not Found",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ResponseStatusException.class)))})
  @PostMapping("distinguish")
  CommentResponse distinguish(@Valid @RequestBody DistinguishComment distinguishCommentForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    rolePermissionService.isPermitted(person,
        Collections.setOf(RolePermissionCommentTypes.ADMIN_SPEAK,
            RolePermissionCommentTypes.MODERATOR_SPEAK),
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    // @todo: check if he is mod if not ADMIN_SPEAK
    final Comment comment = commentRepository.findById((long) distinguishCommentForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    comment.setFeatured(distinguishCommentForm.distinguished());

    commentService.updateComment(comment);

    return CommentResponse.builder()
        .comment_view(lemmyCommentService.createCommentView(comment, comment.getPerson()))
        .build();
  }

  /**
   * Resolve a comment report. Only a mod can do this.
   *
   * @param resolveCommentReportForm The form containing the report ID and resolved state. (type:
   *                                 ResolveCommentReport)
   * @param principal                The authenticated user. (type: JwtPerson)
   * @return The updated comment report response. (type: CommentReportResponse)
   */
  @Operation(summary = "Resolve a comment report. Only a mod can do this.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentReportResponse.class))})})
  @PutMapping("report/resolve")
  CommentReportResponse reportResolve(
      @Valid @RequestBody ResolveCommentReport resolveCommentReportForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    rolePermissionService.isPermitted(person,
        Collections.setOf(RolePermissionCommunityTypes.REPORT_COMMUNITY_RESOLVE,
            RolePermissionInstanceTypes.REPORT_INSTANCE_RESOLVE),
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final CommentReport commentReport = commentReportRepository.findById(
            (long) resolveCommentReportForm.report_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    final boolean isAdmin = rolePermissionService.isPermitted(person,
        RolePermissionInstanceTypes.REPORT_INSTANCE_RESOLVE);

    if (!isAdmin) {
      final boolean isModerator = linkPersonCommunityService.hasLink(commentReport.getComment()
          .getCommunity(), person, LinkPersonCommunityType.moderator)
          || linkPersonCommunityService.hasLink(commentReport.getComment()
          .getCommunity(), person, LinkPersonCommunityType.owner);
      if (!isModerator) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }
    }

    commentReport.setResolved(resolveCommentReportForm.resolved());
    commentReport.setResolver(person);
    commentReportService.updateCommentReport(commentReport);

    return CommentReportResponse.builder()
        .comment_report_view(lemmyCommentReportService.createCommentReportView(commentReport,
            commentReport.getCreator()))
        .build();
  }

  /**
   * Retrieves a list of comment reports based on the search criteria.
   *
   * @param listCommentReportsForm The form containing the search criteria.
   * @param principal              The authenticated user.
   * @return A response containing a list of comment report views.
   */
  @Operation(summary = "List comment reports.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ListCommentReportsResponse.class))})})
  @GetMapping("report/list")
  ListCommentReportsResponse reportList(@Valid ListCommentReports listCommentReportsForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    rolePermissionService.isPermitted(person,
        Set.of(RolePermissionCommunityTypes.REPORT_COMMUNITY_READ,
            RolePermissionInstanceTypes.REPORT_INSTANCE_READ),
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final boolean isAdmin = rolePermissionService.isPermitted(person,
        RolePermissionInstanceTypes.REPORT_INSTANCE_READ);

    final List<CommentReport> commentReports = new ArrayList<>();

    final int page = PaginationControllerUtils.getAbsoluteMinNumber(listCommentReportsForm.page(),
        1);
    final int limit = PaginationControllerUtils.getAbsoluteMinNumber(listCommentReportsForm.limit(),
        20);
    if (!isAdmin) {
      final List<Community> moderatingCommunities = new ArrayList<>();

      if (listCommentReportsForm.community_id() == null) {

        moderatingCommunities.addAll(linkPersonCommunityService.getLinks(person,
                LinkPersonCommunityType.owner)
            .stream()
            .map(LinkPersonCommunity::getCommunity)
            .toList());
        moderatingCommunities.addAll(linkPersonCommunityService.getLinks(person,
                LinkPersonCommunityType.moderator)
            .stream()
            .map(LinkPersonCommunity::getCommunity)
            .toList());
      } else {
        Community community = communityRepository.findById(
                (long) listCommentReportsForm.community_id())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));
        if (!linkPersonCommunityService.hasLink(community, person, LinkPersonCommunityType.owner)
            && !linkPersonCommunityService.hasLink(community, person,
            LinkPersonCommunityType.moderator)) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        moderatingCommunities.add(community);
      }
      commentReports.addAll(commentReportRepository.allCommentReportsBySearchCriteria(
          CommentReportSearchCriteria.builder()
              .unresolvedOnly(listCommentReportsForm.unresolved_only() == null
                  || listCommentReportsForm.unresolved_only())
              .perPage(limit)
              .page(page)
              .community(moderatingCommunities)
              .build()));
    } else {
      if (listCommentReportsForm.community_id() != null) {
        Community community = communityRepository.findById(
                (long) listCommentReportsForm.community_id())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));
        commentReports.addAll(commentReportRepository.allCommentReportsBySearchCriteria(
            CommentReportSearchCriteria.builder()
                .unresolvedOnly(listCommentReportsForm.unresolved_only() != null
                    && listCommentReportsForm.unresolved_only())
                .perPage(limit)
                .page(page)
                .community(List.of(community))
                .build()));
      } else {
        commentReports.addAll(commentReportRepository.allCommentReportsBySearchCriteria(
            CommentReportSearchCriteria.builder()
                .unresolvedOnly(listCommentReportsForm.unresolved_only() != null
                    && listCommentReportsForm.unresolved_only())
                .perPage(limit)
                .page(page)
                .build()));
      }
    }

    List<CommentReportView> commentReportViews = new ArrayList<>();

    for (CommentReport commentReport : commentReports) {
      commentReportViews.add(lemmyCommentReportService.createCommentReportView(commentReport,
          commentReport.getCreator()));
    }

    return ListCommentReportsResponse.builder()
        .comment_reports(commentReportViews)
        .build();
  }
}
