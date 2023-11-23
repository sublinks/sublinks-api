package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReports;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ResolveCommentReport;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.comment.services.CommentReportService;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/comment")
@Tag(name = "Comment")
public class CommentModActionsController extends AbstractLemmyApiController {

  private final LemmyCommentReportService lemmyCommentReportService;
  private final CommentReportRepository commentReportRepository;
  private final CommentReportService commentReportService;
  private final AuthorizationService authorizationService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final CommunityRepository communityRepository;

  @Operation(summary = "A moderator remove for a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("remove")
  CommentResponse remove() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Distinguishes a comment (speak as moderator).")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("distinguish")
  CommentResponse distinguish() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Resolve a comment report. Only a mod can do this.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentReportResponse.class))})})
  @PutMapping("report/resolve")
  CommentReportResponse reportResolve(
      @Valid @RequestBody ResolveCommentReport resolveCommentReportForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final CommentReport commentReport = commentReportRepository.findById(
            (long) resolveCommentReportForm.report_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    final boolean isAdmin = authorizationService.isAdmin(person);

    if (!isAdmin) {
      final boolean isModerator =
          linkPersonCommunityService.hasLink(person, commentReport.getComment().getCommunity(),
              LinkPersonCommunityType.moderator) || linkPersonCommunityService.hasLink(person,
              commentReport.getComment().getCommunity(), LinkPersonCommunityType.owner);
      if (!isModerator) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }

    }

    commentReport.setResolved(resolveCommentReportForm.resolved());
    commentReport.setResolver(person);
    commentReportService.updateCommentReport(commentReport);

    return CommentReportResponse.builder().comment_report_view(
        lemmyCommentReportService.createCommentReportView(commentReport,
            commentReport.getCreator())).build();
  }

  @Operation(summary = "List comment reports.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListCommentReportsResponse.class))})})
  @GetMapping("report/list")
  ListCommentReportsResponse reportList(@Valid ListCommentReports listCommentReportsForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final boolean isAdmin = authorizationService.isAdmin(person);

    final List<CommentReport> commentReports = new ArrayList<>();

    if (!isAdmin) {
      final List<Community> moderatingCommunities = new ArrayList<>();
      moderatingCommunities.addAll(
          linkPersonCommunityService.getPersonLinkByType(person, LinkPersonCommunityType.owner));
      moderatingCommunities.addAll(linkPersonCommunityService.getPersonLinkByType(person,
          LinkPersonCommunityType.moderator));
      commentReports.addAll(commentReportRepository.allCommentReportsBySearchCriteria(
          CommentReportSearchCriteria.builder().unresolvedOnly(
                  listCommentReportsForm.unresolved_only() == null
                      || listCommentReportsForm.unresolved_only())
              .perPage(listCommentReportsForm.limit()).page(listCommentReportsForm.page())
              .community(moderatingCommunities).build()));
    } else {
      commentReports.addAll(commentReportRepository.allCommentReportsBySearchCriteria(
          CommentReportSearchCriteria.builder().unresolvedOnly(
                  listCommentReportsForm.unresolved_only() != null
                      && listCommentReportsForm.unresolved_only())
              .perPage(listCommentReportsForm.limit()).page(listCommentReportsForm.page())
              .build()));
    }

    List<CommentReportView> commentReportViews = new ArrayList<>();

    for (CommentReport commentReport : commentReports) {
      commentReportViews.add(lemmyCommentReportService.createCommentReportView(commentReport,
          commentReport.getCreator()));
    }

    return ListCommentReportsResponse.builder().comment_reports(commentReportViews).build();
  }
}
