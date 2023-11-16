package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateCommentLike;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.EditComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.MarkCommentReplyAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.comment.services.CommentReadService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/comment")
@Tag(name = "comment", description = "the comment API")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final LemmyCommentService lemmyCommentService;
    private final PostRepository postRepository;
    private final LanguageRepository languageRepository;
    private final ConversionService conversionService;
    private final CommentLikeService commentLikeService;
    private final PersonService personService;
    private final CommentReadService commentReadService;
    private final AuthorizationService authorizationService;

    @PostMapping
    @Transactional
    public CommentResponse create(@Valid @RequestBody final CreateComment createCommentForm, final JwtPerson principal) {

        // @todo auth service
        final Person person = (Person) principal.getPrincipal();
        final Post post = postRepository.findById((long) createCommentForm.post_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
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
                .commentBody(createCommentForm.content())
                .activityPubId("")
                .post(post)
                .community(post.getCommunity())
                .language(language.get())
                .build();

        if (createCommentForm.parent_id() != null) {
            Optional<Comment> parentComment = commentRepository.findById((long) createCommentForm.parent_id());
            if (parentComment.isEmpty()) {
                throw new RuntimeException("Invalid comment parent.");
            }
            commentService.createComment(comment, parentComment.get());
        } else {
            commentService.createComment(comment);
        }
        commentLikeService.updateOrCreateCommentLikeLike(comment, person);

        final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
        return CommentResponse.builder()
                .comment_view(commentView)
                .recipient_ids(new ArrayList<>())
                .build();
    }

    @PutMapping
    CommentResponse update(@Valid @RequestBody final EditComment editCommentForm, final JwtPerson principal) {

        Person person = Optional.ofNullable((Person) principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Comment comment = commentRepository.findById((long) editCommentForm.comment_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.update)
                .onEntity(comment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        comment.setCommentBody(editCommentForm.content());
        Optional<Language> language;
        if (editCommentForm.language_id() != null) {
            language = languageRepository.findById((long) editCommentForm.language_id());
        } else {
            language = personService.getPersonDefaultPostLanguage(person, comment.getPost().getCommunity());
        }
        if (language.isEmpty()) {
            throw new RuntimeException("No language selected");
        }
        comment.setLanguage(language.get());

        commentService.updateComment(comment);

        final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
        return CommentResponse.builder()
                .comment_view(commentView)
                .recipient_ids(new ArrayList<>())
                .build();
    }

    @PostMapping("delete")
    CommentResponse delete() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_as_read")
    CommentResponse markAsRead(@Valid @RequestBody final MarkCommentReplyAsRead markCommentReplyAsRead, final JwtPerson principal) {

        Optional<Comment> comment = commentRepository.findById((long) markCommentReplyAsRead.comment_reply_id());
        if (comment.isEmpty() || principal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        final Person person = (Person) principal.getPrincipal();
        commentReadService.markCommentReadByPerson(comment.get(), person);

        final CommentView commentView = lemmyCommentService.createCommentView(comment.get(), person);
        return CommentResponse.builder()
                .comment_view(commentView)
                .recipient_ids(new ArrayList<>())
                .build();
    }

    @PostMapping("like")
    CommentResponse like(@Valid @RequestBody CreateCommentLike createCommentLikeForm, JwtPerson jwtPerson) {

        final Person person = (Person) jwtPerson.getPrincipal();
        final Optional<Comment> comment = commentRepository.findById(createCommentLikeForm.comment_id());
        if (createCommentLikeForm.score() == 1) {
            commentLikeService.updateOrCreateCommentLikeLike(comment.get(), person);
        } else if (createCommentLikeForm.score() == -1) {
            commentLikeService.updateOrCreateCommentLikeDislike(comment.get(), person);
        } else {
            commentLikeService.updateOrCreateCommentLikeNeutral(comment.get(), person);
        }

        final CommentView commentView = lemmyCommentService.createCommentView(comment.get(), person);
        return CommentResponse.builder()
                .comment_view(commentView)
                .recipient_ids(new ArrayList<>())
                .build();
    }

    @PutMapping("save")
    CommentResponse saveForLater() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("list")
    GetCommentsResponse list(@Valid final GetComments getCommentsForm, final JwtPerson principal) {

        final Post post = postRepository.findById((long) getCommentsForm.post_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Person person = null;
        if (principal != null) {
            person = (Person) principal.getPrincipal();
        }

        final CommentSortType sortType = conversionService.convert(getCommentsForm.sort(), CommentSortType.class);
        final ListingType listingType = conversionService.convert(getCommentsForm.type_(), ListingType.class);

        final CommentSearchCriteria commentRepositorySearch = CommentSearchCriteria.builder()
                .page(1)
                .listingType(listingType)
                .perPage(20)
                .commentSortType(sortType)
                .post(post)
                .build();

        final List<Comment> comments = commentRepository.allCommentsBySearchCriteria(commentRepositorySearch);
        final List<CommentView> commentViews = new ArrayList<>();
        for (Comment comment : comments) {
            CommentView commentView;
            if (person != null) {
                commentView = lemmyCommentService.createCommentView(comment, person);
                commentReadService.markCommentReadByPerson(comment, person);
            } else {
                commentView = lemmyCommentService.createCommentView(comment);
            }
            commentViews.add(commentView);
        }
        return GetCommentsResponse.builder().comments(commentViews).build();
    }

    @PostMapping("report")
    CommentReportResponse report() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
