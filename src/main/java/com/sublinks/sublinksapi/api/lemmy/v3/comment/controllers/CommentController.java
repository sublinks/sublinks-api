package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateCommentLike;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentLikeService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
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
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final LemmyCommentService lemmyCommentService;
    private final PostRepository postRepository;
    private final LanguageRepository languageRepository;
    private final ConversionService conversionService;
    private final CommentLikeService commentLikeService;

    @PostMapping
    @Transactional
    public CommentResponse create(@Valid @RequestBody final CreateComment createCommentForm, final JwtPerson principal) {

        final Person person = (Person) principal.getPrincipal();
        final Post post = postRepository.findById((long) createCommentForm.post_id()).get();
        final Language language = languageRepository.findById((long) createCommentForm.language_id()).get();
        final Comment comment = Comment.builder()
                .person(person)
                .isLocal(true)
                .commentBody(createCommentForm.content())
                .activityPubId("")
                .post(post)
                .community(post.getCommunity())
                .language(language)
                .build();

        commentService.createComment(comment);
        String path = null;
        if (createCommentForm.parent_id() != null) {
            Optional<Comment> parentComment = commentRepository.findById((long) createCommentForm.parent_id());
            if (parentComment.isPresent()) {
                path = String.format("%s.%d", parentComment.get().getPath(), comment.getId());
            }
        }
        if (path == null) {
            path = String.format("0.%d", comment.getId());
        }
        comment.setPath(path);
        comment.setActivityPubId(lemmyCommentService.generateActivityPubId(comment));
        commentRepository.saveAndFlush(comment);

        final CommentView commentView = lemmyCommentService.createCommentView(comment, person);
        return CommentResponse.builder()
                .comment_view(commentView)
                .recipient_ids(new ArrayList<>())
                .build();
    }

    @PutMapping
    CommentResponse update() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("delete")
    CommentResponse delete() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_as_read")
    CommentResponse markAsRead() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
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
