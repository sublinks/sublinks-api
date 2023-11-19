package com.sublinks.sublinksapi.api.lemmy.v3.comment.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.ListCommentReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/comment")
@Tag(name = "comment", description = "the comment API")
public class CommentModActionsController extends AbstractLemmyApiController {

    @PostMapping("remove")
    CommentResponse remove() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("distinguish")
    CommentResponse distinguish() {

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
