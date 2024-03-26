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
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentLikes;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentLikesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.MarkCommentReplyAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.VoteView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentReportService;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria.CommentSearchCriteriaBuilder;
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
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final CommentReplyRepository commentReplyRepository;
  private final CommentReplyService commentReplyService;
  private final SlurFilterService slurFilterService;
  private final RoleAuthorizingService roleAuthorizingService;

  @Operation(summary = "Create a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping
  @Transactional
  public CommentResponse create(@Valid @RequestBody final CreateComment createCommentForm,
      final JwtPerson principal) {

    // @todo auth service
    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.CREATE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Post post = postRepository.findById((long) createCommentForm.post_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

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
      throw new RuntimeException("No language selected");
    }

    final Comment comment = Comment.builder()
        .person(person)
        .isLocal(true)
        .activityPubId("")
        .removedState(RemovedState.NOT_REMOVED)
        .post(post)
        .community(post.getCommunity())
        .language(language.get())
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

  @Operation(summary = "Edit a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PutMapping
  CommentResponse update(@Valid @RequestBody final EditComment editCommentForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.CREATE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));
    Comment comment = commentRepository.findById((long) editCommentForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

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

  @Operation(summary = "Delete a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("delete")
  CommentResponse delete(final JwtPerson principal) {
    // @todo Implement delete comment
    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.CREATE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Mark a comment as read.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("mark_as_read")
  CommentResponse markAsRead(
      @Valid @RequestBody final MarkCommentReplyAsRead markCommentReplyAsRead,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowBadRequest(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.MARK_COMMENT_AS_READ,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Comment comment = commentRepository.findById(
            (long) markCommentReplyAsRead.comment_reply_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    commentReadService.markCommentReadByPerson(comment, person);

    final Optional<CommentReply> commentReply = commentReplyRepository.findById(
        (long) markCommentReplyAsRead.comment_reply_id());

    if (commentReply.isPresent()) {
      CommentReply commentReplyEntity = commentReply.get();
      commentReplyEntity.setIsRead(true);
      commentReplyService.updateCommentReply(commentReplyEntity);
    }

    final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Like / Vote on a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PostMapping("like")
  CommentResponse like(@Valid @RequestBody CreateCommentLike createCommentLikeForm,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person,
        switch (createCommentLikeForm.score()) {
          case 1 -> RolePermission.COMMENT_UPVOTE;
          case -1 -> RolePermission.COMMENT_DOWNVOTE;
          case 0 -> RolePermission.COMMENT_NEUTRALVOTE;
          default -> RolePermission.COMMENT_NEUTRALVOTE;
        },

        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

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
    return CommentResponse.builder()
        .comment_view(commentView)
        .recipient_ids(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Get Votes on a comment.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListCommentLikesResponse.class))})})
  @GetMapping("like/list")
  ListCommentLikesResponse listLikes(@Valid final ListCommentLikes listCommentLikesForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.COMMENT_LIST_VOTES,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Comment comment = commentRepository.findById((long) listCommentLikesForm.comment_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    List<CommentLike> likes = commentLikeService.getCommentLikes(comment,
        Math.abs(listCommentLikesForm.page() != null ? listCommentLikesForm.page() : 1),
        Math.abs(listCommentLikesForm.limit() != null ? listCommentLikesForm.limit() : 20));

    final List<VoteView> voteViews = new ArrayList<>();
    for (CommentLike like : likes) {
      voteViews.add(conversionService.convert(like, VoteView.class));
    }
    return ListCommentLikesResponse.builder().comment_likes(voteViews).build();
  }

  @Operation(summary = "Save a comment for later.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponse.class))})})
  @PutMapping("save")
  CommentResponse saveForLater(final JwtPerson principal) {
    // @todo Implement save comment
    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.FAVORITE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Get / fetch comments.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetCommentsResponse.class))})})
  @GetMapping("list")
  GetCommentsResponse list(@Valid final GetComments getCommentsForm, final JwtPerson principal) {

    Optional<Person> person = getOptionalPerson(principal);
    roleAuthorizingService.hasAdminOrPermissionOrThrow(person.orElse(null),
        RolePermission.READ_COMMENT,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Optional<Integer> post_id = Optional.ofNullable(getCommentsForm.post_id());
    final Optional<Integer> page = Optional.ofNullable(getCommentsForm.page());
    final Optional<Integer> perPage = Optional.ofNullable(getCommentsForm.limit());

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

    // @todo Should this really throw a error? Why not just clamp the values?
    if (perPage.isPresent() && (perPage.get() < 1 || perPage.get() > 50)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "couldnt_get_comments");
    }
    searchBuilder.perPage(perPage.orElse(10));

    if (page.isPresent() && page.get() < 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "couldnt_get_comments");
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

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.REPORT_COMMENT,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));

    final Comment comment = commentRepository.findById((long) createCommentReportForm.comment_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    final CommentReport commentReport = CommentReport.builder()
        .comment(comment)
        .creator(person)
        .originalContent(comment.getCommentBody())
        .reason(createCommentReportForm.reason())
        .build();

    commentReportService.createCommentReport(commentReport);

    final CommentReportView commentReportView = lemmyCommentReportService.createCommentReportView(
        commentReport, person);

    return CommentReportResponse.builder().comment_report_view(commentReportView).build();
  }
}
