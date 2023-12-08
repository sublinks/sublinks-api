package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateCommentLike;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateCommentReport;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.EditComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.MarkCommentReplyAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentReplyRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.comment.services.CommentReadService;
import com.sublinks.sublinksapi.comment.services.CommentReplyService;
import com.sublinks.sublinksapi.comment.services.CommentReportService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterBlockedException;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterReportException;
import com.sublinks.sublinksapi.slurfilter.services.SlurFilterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
@RequestMapping(path = "/api/v3/comment")
@Tag(name = "Comment")
public class CommentController extends AbstractLemmyApiController {

  private final CommentRepository commentRepository;
  private final CommentService commentService;
  private final LemmyCommentService lemmyCommentService;
  private final PostRepository postRepository;
  private final LanguageRepository languageRepository;
  private final ConversionService conversionService;
  private final CommentLikeService commentLikeService;
  private final PersonService personService;
  private final CommentReadService commentReadService;
  private final LemmyCommentReportService lemmyCommentReportService;
  private final CommentReportRepository commentReportRepository;
  private final CommentReportService commentReportService;
  private final AuthorizationService authorizationService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final CommentReplyRepository commentReplyRepository;
  private final CommentReplyService commentReplyService;
  private final SlurFilterService slurFilterService;

  @Operation(summary = "Create a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping
  @Transactional
  public CommentResponse create(@Valid @RequestBody final CreateComment createCommentForm,
      final JwtPerson principal) {

    // @todo auth service
    final Person person = getPersonOrThrowUnauthorized(principal);
    final Post post = postRepository.findById((long) createCommentForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    if (post.isLocked()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_locked");
    }

    if (post.isDeleted() || post.isRemoved()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post_not_found");
    }

    // Language
    Optional<Language> language;
    if (createCommentForm.language_id() != null) {
      language = languageRepository.findById((long) createCommentForm.language_id());
    } else {
      language = personService.getPersonDefaultPostLanguage(person, post.getCommunity());
    }

    if (language.isEmpty()) {
      throw new RuntimeException("No language selected");
    }

    final Comment comment = Comment.builder().person(person).isLocal(true).activityPubId("")
        .post(post).community(post.getCommunity()).language(language.get()).build();
    boolean shouldReport = false;
    try {
      comment.setCommentBody(slurFilterService.censorText(createCommentForm.content()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      comment.setCommentBody(createCommentForm.content());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_blocked_by_slur_filter");
    }

    if (createCommentForm.parent_id() != null) {
      Optional<Comment> parentComment = commentRepository.findById(
          (long) createCommentForm.parent_id());
      if (parentComment.isEmpty()) {
        throw new RuntimeException("Invalid comment parent.");
      }
      commentService.createComment(comment, parentComment.get());
    } else {
      commentService.createComment(comment);
    }
    commentLikeService.updateOrCreateCommentLikeLike(comment, person);

    if (shouldReport) {
      commentReportService.createCommentReport(
          CommentReport.builder().comment(comment).creator(person)
              .originalContent(comment.getCommentBody())
              .reason("AUTOMATED: Comment creation triggered slur filter").build());
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder().comment_view(commentView).recipient_ids(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Edit a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PutMapping
  CommentResponse update(@Valid @RequestBody final EditComment editCommentForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    Comment comment = commentRepository.findById((long) editCommentForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    authorizationService.canPerson(person).performTheAction(AuthorizeAction.update)
        .onEntity(comment).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    Optional<Language> language;
    if (editCommentForm.language_id() != null) {
      language = languageRepository.findById((long) editCommentForm.language_id());
    } else {
      language = personService.getPersonDefaultPostLanguage(person,
          comment.getPost().getCommunity());
    }
    if (language.isEmpty()) {
      throw new RuntimeException("No language selected");
    }
    comment.setLanguage(language.get());

    boolean shouldReport = false;

    try {
      comment.setCommentBody(slurFilterService.censorText(editCommentForm.content()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      comment.setCommentBody(editCommentForm.content());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_blocked_by_slur_filter");
    }

    commentService.updateComment(comment);

    if (shouldReport) {
      commentReportService.createCommentReport(
          CommentReport.builder().comment(comment).creator(person)
              .originalContent(comment.getCommentBody())
              .reason("AUTOMATED: Comment update triggered slur filter").build());
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder().comment_view(commentView).recipient_ids(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Delete a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("delete")
  CommentResponse delete() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Mark a comment as read.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("mark_as_read")
  CommentResponse markAsRead(
      @Valid @RequestBody final MarkCommentReplyAsRead markCommentReplyAsRead,
      final JwtPerson principal) {

    final Comment comment = commentRepository.findById(
            (long) markCommentReplyAsRead.comment_reply_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    final Person person = getPersonOrThrowBadRequest(principal);
    commentReadService.markCommentReadByPerson(comment, person);

    final Optional<CommentReply> commentReply = commentReplyRepository.findById(
        (long) markCommentReplyAsRead.comment_reply_id());

    if (commentReply.isPresent()) {
      CommentReply commentReplyEntity = commentReply.get();
      commentReplyEntity.setIsRead(true);
      commentReplyService.updateCommentReply(commentReplyEntity);
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder().comment_view(commentView).recipient_ids(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Like / Vote on a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("like")
  CommentResponse like(@Valid @RequestBody CreateCommentLike createCommentLikeForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    final Comment comment = commentRepository.findById(createCommentLikeForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    if (createCommentLikeForm.score() == 1) {
      commentLikeService.updateOrCreateCommentLikeLike(comment, person);
    } else if (createCommentLikeForm.score() == -1) {
      commentLikeService.updateOrCreateCommentLikeDislike(comment, person);
    } else {
      commentLikeService.updateOrCreateCommentLikeNeutral(comment, person);
    }
    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder().comment_view(commentView).recipient_ids(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Save a comment for later.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PutMapping("save")
  CommentResponse saveForLater() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Get / fetch comments.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetCommentsResponse.class))})})
  @GetMapping("list")
  GetCommentsResponse list(@Valid final GetComments getCommentsForm, final JwtPerson principal) {

    final Post post = postRepository.findById((long) getCommentsForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    Optional<Person> person = getOptionalPerson(principal);

    final CommentSortType sortType = conversionService.convert(getCommentsForm.sort(),
        CommentSortType.class);
    final ListingType listingType = conversionService.convert(getCommentsForm.type_(),
        ListingType.class);

    final CommentSearchCriteria commentRepositorySearch = CommentSearchCriteria.builder().page(1)
        .listingType(listingType).perPage(20).commentSortType(sortType).post(post).build();

    final List<Comment> comments = commentRepository.allCommentsBySearchCriteria(
        commentRepositorySearch);
    final List<CommentView> commentViews = new ArrayList<>();
    for (Comment comment : comments) {
      CommentView commentView;
      if (person.isPresent()) {
        commentView = lemmyCommentService.createCommentView(comment, person.get());
        commentReadService.markCommentReadByPerson(comment, person.get());
      } else {
        commentView = lemmyCommentService.createCommentView(comment);
      }
      commentViews.add(commentView);
    }
    return GetCommentsResponse.builder().comments(commentViews).build();
  }

  @Operation(summary = "Report a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentReportResponse.class))})})
  @PostMapping("report")
  CommentReportResponse report(
      @Valid @RequestBody final CreateCommentReport createCommentReportForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    final Comment comment = commentRepository.findById((long) createCommentReportForm.comment_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    final CommentReport commentReport = CommentReport.builder().comment(comment).creator(person)
        .originalContent(comment.getCommentBody()).reason(createCommentReportForm.reason()).build();

    commentReportService.createCommentReport(commentReport);

    final CommentReportView commentReportView = lemmyCommentReportService.createCommentReportView(
        commentReport, person);

    return CommentReportResponse.builder().comment_report_view(commentReportView).build();
  }
}
