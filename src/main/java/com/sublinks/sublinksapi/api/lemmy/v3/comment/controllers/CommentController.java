package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers.CommentMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers.LemmyCommentMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.comment.CommentRepository;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.language.LanguageRepository;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.post.PostRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@RestController
@RequestMapping(path = "/api/v3/comment")
public class CommentController {

    final private CommentRepository commentRepository;

    final private LemmyCommentMapper lemmyCommentMapper;

    final private CommentMapper commentMapper;

    final private LemmyCommentService lemmyCommentService;

    final private PostRepository postRepository;

    final private LanguageRepository languageRepository;

    public CommentController(CommentRepository commentRepository, LemmyCommentMapper lemmyCommentMapper, CommentMapper commentMapper, LemmyCommentService lemmyCommentService, PostRepository postRepository, LanguageRepository languageRepository) {
        this.commentRepository = commentRepository;
        this.lemmyCommentMapper = lemmyCommentMapper;
        this.commentMapper = commentMapper;
        this.lemmyCommentService = lemmyCommentService;
        this.postRepository = postRepository;
        this.languageRepository = languageRepository;
    }

    @PostMapping
    @Transactional
    public CommentResponse create(@Valid @RequestBody CreateComment createCommentForm, UsernamePasswordAuthenticationToken principal) {
        Person person = (Person) principal.getPrincipal();
        Post post = postRepository.findById((long)createCommentForm.post_id()).get();
        Language language = languageRepository.findById((long)createCommentForm.language_id()).get();
        Comment comment = Comment.builder()
                .person(person)
                .commentBody(createCommentForm.content())
                .activityPubId("")
                .post(post)
                .community(post.getCommunity())
                .language(language)
                .path(post.getCommunity().getId() + ";" + post.getId())
                .build();
        commentRepository.saveAndFlush(comment);
        CommentView commentView = lemmyCommentService.createCommentView(comment, person);
        return CommentResponse.builder()
                .comment_view(commentView)
                .form_id(createCommentForm.form_id())
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

    @PostMapping("remove")
    CommentResponse remove() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_as_read")
    CommentResponse markAsRead() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("distinguish")
    CommentResponse distinguish() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("like")
    CommentResponse like() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("save")
    CommentResponse saveForLater() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("list")
    GetCommentsResponse list(@Valid GetComments getCommentsForm,
                             UsernamePasswordAuthenticationToken principal) {
        List<Comment> comments = commentRepository.findAll();
        List<CommentView> commentViews = new ArrayList<>();
        for (Comment comment : comments) {
            CommentView commentView = lemmyCommentService.createCommentView(comment, (Person) principal.getPrincipal());
            commentViews.add(commentView);
        }
        return GetCommentsResponse.builder().comments(commentViews).build();
    }

    @PostMapping("report")
    CommentReportResponse report() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("report/resolve")
    CommentReportResponse reportResolve() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report/list")
    ListCommentReportsResponse reportList() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
