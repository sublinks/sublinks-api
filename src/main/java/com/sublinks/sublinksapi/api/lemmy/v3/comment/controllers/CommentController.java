package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateCommentLike;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateCommentReport;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.DeleteComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.EditComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentLikes;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentLikesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.MarkCommentReplyAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.SaveComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.VoteView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommentTypes;
import com.sublinks.sublinksapi.authorization.services.AclService;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentLike;
import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.comment.entities.CommentReport;
import com.sublinks.sublinksapi.comment.entities.CommentSave;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria.CommentSearchCriteriaBuilder;
import com.sublinks.sublinksapi.comment.repositories.ComentSaveRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentReplyRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.comment.services.CommentReadService;
import com.sublinks.sublinksapi.comment.services.CommentReplyService;
import com.sublinks.sublinksapi.comment.services.CommentReportService;
import com.sublinks.sublinksapi.comment.services.CommentSaveService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.shared.RemovedState;
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

/**
 * Controller class for managing comments.
 */
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
  private final CommentReportService commentReportService;
  private final CommentReplyRepository commentReplyRepository;
  private final CommentReplyService commentReplyService;
  private final SlurFilterService slurFilterService;
  private final RolePermissionService rolePermissionService;
  private final CommentSaveService commentSaveForLaterService;
  private final ComentSaveRepository commentSaveForLaterRepository;
  private final AclService aclService;

  /**
   * Creates a comment.
   *
   * @param createCommentForm The form data for creating a comment.
   * @param principal         The authenticated user.
   * @return The created comment response.
   */
  @Operation(summary = "Create a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping
  @Transactional
  public CommentResponse create(@Valid @RequestBody final CreateComment createCommentForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Post post = postRepository.findById((long) createCommentForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.CREATE_COMMENT)
        .onCommunity(post.getCommunity())
        .check()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    if (post.isDeleted() || post.isRemoved()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post_not_found");
    }

    if (post.isLocked()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_locked");
    }

    // Language
    Optional<Language> language;
    if (createCommentForm.language_id() != null) {
      language = languageRepository.findById((long) createCommentForm.language_id());
    } else {
      language = personService.getPersonDefaultPostLanguage(person, post.getCommunity());
    }

    if (language.isEmpty()) {
      language = Optional.ofNullable(languageRepository.findLanguageByCode("und"));
    }

    final Comment comment = Comment.builder()
        .person(person)
        .isLocal(true)
        .activityPubId("")
        .removedState(RemovedState.NOT_REMOVED)
        .post(post)
        .community(post.getCommunity())
        .language(language.orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found")))
        .build();
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
      commentReportService.createCommentReport(CommentReport.builder()
          .comment(comment)
          .creator(person)
          .originalContent(comment.getCommentBody())
          .reason("AUTOMATED: Comment creation triggered slur filter")
          .build());
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  /**
   * Updates a comment.
   *
   * @param editCommentForm The form data for editing a comment.
   * @param principal       The authenticated user.
   * @return The updated comment response.
   * @throws ResponseStatusException If the user is not authorized or the comment or language is not
   *                                 found.
   * @throws ResponseStatusException If the comment is blocked by the slur filter.
   */
  @Operation(summary = "Edit a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))})})
  @PutMapping
  CommentResponse update(@Valid @RequestBody final EditComment editCommentForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    Comment comment = commentRepository.findById((long) editCommentForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.UPDATE_COMMENT)
        .onEntity(comment)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    Optional<Language> language;
    if (editCommentForm.language_id() != null) {
      language = languageRepository.findById((long) editCommentForm.language_id());
    } else {
      language = Optional.ofNullable(comment.getLanguage());
    }
    if (language.isEmpty()) {
      language = Optional.ofNullable(languageRepository.findLanguageByCode("und"));
    }

    comment.setLanguage(language.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found")));

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
      commentReportService.createCommentReport(CommentReport.builder()
          .comment(comment)
          .creator(person)
          .originalContent(comment.getCommentBody())
          .reason("AUTOMATED: Comment update triggered slur filter")
          .build());
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  /**
   * Deletes a comment.
   *
   * @param deleteCommentForm The form data for deleting a comment.
   * @param principal         The authenticated user.
   * @return The response after deleting a comment.
   * @throws ResponseStatusException If the user is not authorized or the comment is not found.
   */
  @Operation(summary = "Delete a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("delete")
  CommentResponse delete(@Valid @RequestBody final DeleteComment deleteCommentForm,
      final JwtPerson principal) {
    // @todo Implement delete comment
    final Person person = getPersonOrThrowUnauthorized(principal);

    final Optional<Comment> comment = commentRepository.findById(
        (long) deleteCommentForm.comment_id());
    if (comment.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found");
    }

    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.DELETE_COMMENT)
        .onEntity(comment.get())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Comment commentEntity = comment.get();
    if (commentEntity.getPerson() != person) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
    }

    commentEntity.setDeleted(true);

    commentService.updateComment(commentEntity);

    final CommentView commentView = lemmyCommentService.createCommentView(commentEntity, person);

    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  /**
   * Mark a comment as read.
   *
   * @param markCommentReplyAsRead The data for marking a comment as read.
   * @param principal              The authenticated user.
   * @return The comment response after marking the comment as read.
   */
  @Operation(summary = "Mark a comment as read.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("mark_as_read")
  CommentResponse markAsRead(
      @Valid @RequestBody final MarkCommentReplyAsRead markCommentReplyAsRead,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowBadRequest(principal);

    final Comment comment = commentRepository.findById(
            (long) markCommentReplyAsRead.comment_reply_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    commentReadService.markCommentReadByPerson(comment, person);

    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.MARK_COMMENT_AS_READ)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Optional<CommentReply> commentReply = commentReplyRepository.findById(
        (long) markCommentReplyAsRead.comment_reply_id());

    if (commentReply.isPresent()) {
      CommentReply commentReplyEntity = commentReply.get();
      commentReplyEntity.setRead(true);
      commentReplyService.updateCommentReply(commentReplyEntity);
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  /**
   * Like / Vote on a comment.
   *
   * @param createCommentLikeForm The data for liking/voting on a comment.
   * @param principal             The authenticated user.
   * @return The CommentResponse after liking/voting on the comment.
   */
  @Operation(summary = "Like / Vote on a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("like")
  CommentResponse like(@Valid @RequestBody CreateCommentLike createCommentLikeForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    RolePermissionCommentTypes roleType = switch (createCommentLikeForm.score()) {
      case 1 -> RolePermissionCommentTypes.COMMENT_UPVOTE;
      case -1 -> RolePermissionCommentTypes.COMMENT_DOWNVOTE;
      default -> RolePermissionCommentTypes.COMMENT_NEUTRALVOTE;
    };

    final Comment comment = commentRepository.findById(createCommentLikeForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    aclService.canPerson(person)
        .performTheAction(roleType)
        .onCommunity(comment.getCommunity())
        .check()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    if (createCommentLikeForm.score() == 1) {
      commentLikeService.updateOrCreateCommentLikeLike(comment, person);
    } else if (createCommentLikeForm.score() == -1) {
      commentLikeService.updateOrCreateCommentLikeDislike(comment, person);
    } else {
      commentLikeService.updateOrCreateCommentLikeNeutral(comment, person);
    }
    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  /**
   * Retrieves the list of likes/votes on a comment.
   *
   * @param listCommentLikesForm The form data for listing comment likes/votes.
   * @param principal            The authenticated user passed as a JwtPerson object.
   * @return The response containing the list of comment likes/votes.
   */
  @Operation(summary = "Get Votes on a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ListCommentLikesResponse.class))})})
  @GetMapping("like/list")
  ListCommentLikesResponse listLikes(@Valid final ListCommentLikes listCommentLikesForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Comment comment = commentRepository.findById((long) listCommentLikesForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.COMMENT_LIST_VOTES)
        .onCommunity(comment.getCommunity())
        .check()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    List<CommentLike> likes = commentLikeService.getCommentLikes(comment,
        Math.abs(listCommentLikesForm.page() != null ? listCommentLikesForm.page() : 1),
        Math.abs(listCommentLikesForm.limit() != null ? listCommentLikesForm.limit() : 20));

    final List<VoteView> voteViews = new ArrayList<>();
    for (CommentLike like : likes) {
      voteViews.add(conversionService.convert(like, VoteView.class));
    }
    return ListCommentLikesResponse.builder()
        .comment_likes(voteViews)
        .build();
  }

  /**
   * Save a comment for later.
   *
   * @param saveCommentForm The form data for saving a comment.
   * @param principal       The authenticated user.
   * @return The comment response after saving the comment.
   */
  @Operation(summary = "Save a comment for later.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentResponse.class))})})
  @PutMapping("save")
  CommentResponse saveForLater(@Valid @RequestBody final SaveComment saveCommentForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    Comment comment = commentRepository.findById((long) saveCommentForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.FAVORITE_COMMENT)
        .onCommunity(comment.getCommunity())
        .check()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    Optional<CommentSave> commentSaveForLater = commentSaveForLaterRepository.findFirstByPersonAndComment(
        person, comment);

    if (saveCommentForm.save()) {
      if (commentSaveForLater.isPresent()) {
        return CommentResponse.builder()
            .comment_view(lemmyCommentService.createCommentView(comment, person))
            .recipient_ids(new ArrayList<>())
            .build();
      }
      commentSaveForLaterService.createCommentSave(CommentSave.builder()
          .comment(comment)
          .person(person)
          .build());
    } else {
      if (commentSaveForLater.isEmpty()) {
        return CommentResponse.builder()
            .comment_view(lemmyCommentService.createCommentView(comment, person))
            .recipient_ids(new ArrayList<>())
            .build();
      }

      commentSaveForLaterService.deleteCommentSave(commentSaveForLater.get());
    }

    return CommentResponse.builder()
        .comment_view(lemmyCommentService.createCommentView(comment, person))
        .recipient_ids(new ArrayList<>())
        .build();
  }


  /**
   * Retrieves the list of comments based on the provided criteria.
   *
   * @param getCommentsForm The form data for retrieving comments.
   * @param principal       The authenticated user.
   * @return The response containing the list of comments.
   * @throws ResponseStatusException If the user is not authorized or the post is not found.
   * @throws ResponseStatusException If the provided pagination parameters are invalid.
   */
  @Operation(summary = "Get / fetch comments.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = GetCommentsResponse.class))})})
  @GetMapping("list")
  GetCommentsResponse list(@Valid final GetComments getCommentsForm, final JwtPerson principal) {

    Optional<Person> person = getOptionalPerson(principal);
    aclService.canPerson(person.orElse(null))
        .performTheAction(RolePermissionCommentTypes.READ_COMMENT)
        .check()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Optional<Integer> post_id = Optional.ofNullable(getCommentsForm.post_id());
    Optional<Integer> page = Optional.ofNullable(getCommentsForm.page());
    Optional<Integer> perPage = Optional.ofNullable(getCommentsForm.limit());

    final CommentSortType sortType = conversionService.convert(getCommentsForm.sort(),
        CommentSortType.class);
    final ListingType listingType = conversionService.convert(getCommentsForm.type_(),
        ListingType.class);

    final CommentSearchCriteriaBuilder searchBuilder = CommentSearchCriteria.builder()
        .listingType(listingType)
        .commentSortType(sortType);

    if (post_id.isPresent()) {
      final Optional<Post> post = postRepository.findById((long) post_id.get());
      if (post.isPresent()) {
        searchBuilder.post(post.get());
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_not_found");
      }
    }

    searchBuilder.maxDepth(
        getCommentsForm.max_depth() != null ? Math.max(Math.min(getCommentsForm.max_depth(), 2), 0)
            : 2);

    if (perPage.isPresent()) {
      if (perPage.get() < 1) {
        perPage = Optional.of(1);
      }
      if (perPage.get() > 50) {
        page = Optional.of(50);
      }
    }
    searchBuilder.perPage(perPage.orElse(10));

    if (page.isPresent() && page.get() < 1) {
      page = Optional.of(1);
    }
    searchBuilder.page(page.orElse(1));

    final CommentSearchCriteria commentRepositorySearch = searchBuilder.build();

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
    return GetCommentsResponse.builder()
        .comments(commentViews)
        .build();
  }

  /**
   * Reports a comment.
   *
   * @param createCommentReportForm The data to create a comment report.
   * @param principal               The authenticated user.
   * @return The comment report response.
   * @throws ResponseStatusException If the user is not authorized, the comment is not found, or the
   *                                 request is invalid.
   */
  @Operation(summary = "Report a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      description = "OK",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CommentReportResponse.class))})})
  @PostMapping("report")
  CommentReportResponse report(
      @Valid @RequestBody final CreateCommentReport createCommentReportForm,
      final JwtPerson principal) {

    final Comment comment = commentRepository.findById((long) createCommentReportForm.comment_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    final Person person = getPersonOrThrowUnauthorized(principal);

    aclService.canPerson(person)
        .performTheAction(RolePermissionCommentTypes.REPORT_COMMENT)
        .onCommunity(comment.getCommunity())
        .check()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final CommentReport commentReport = CommentReport.builder()
        .comment(comment)
        .creator(person)
        .originalContent(comment.getCommentBody())
        .reason(createCommentReportForm.reason())
        .build();

    commentReportService.createCommentReport(commentReport);

    final CommentReportView commentReportView = lemmyCommentReportService.createCommentReportView(
        commentReport, person);

    return CommentReportResponse.builder()
        .comment_report_view(commentReportView)
        .build();
  }
}
