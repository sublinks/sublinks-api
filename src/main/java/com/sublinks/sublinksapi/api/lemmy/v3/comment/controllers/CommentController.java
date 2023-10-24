package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers.CommentMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers.LemmyCommentMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetCommentsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.comment.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    public CommentController(CommentRepository commentRepository, LemmyCommentMapper lemmyCommentMapper, CommentMapper commentMapper, LemmyCommentService lemmyCommentService) {
        this.commentRepository = commentRepository;
        this.lemmyCommentMapper = lemmyCommentMapper;
        this.commentMapper = commentMapper;
        this.lemmyCommentService = lemmyCommentService;
    }

    @PostMapping
    CommentResponse create() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
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
    GetCommentsResponse list(@Valid GetComments getCommentsForm) {
        List<Comment> comments = commentRepository.findAll();
        List<CommentView> commentViews = new ArrayList<>();
        for (Comment comment : comments) {
            //@todo commentview
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
