package com.fedilinks.fedilinksapi.api.lemmy.v3.controllers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetPostResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetPostsResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetSiteMetadataResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.ListPostReportsResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.PostReportResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.PostResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PostView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping(path = "/api/v3/post")
public class PostController {
    @GetMapping
    GetPostResponse show() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping
    PostResponse update() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("delete")
    PostResponse delete() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("remove")
    PostResponse remove() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_as_read")
    PostResponse markAsRead() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("lock")
    PostResponse lock() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("feature")
    PostResponse feature() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("list")
    GetPostsResponse index() {
        Collection<PostView> posts = new HashSet<>();
        return GetPostsResponse.builder()
                .posts(posts)
                .build();
    }

    @PostMapping("like")
    PostResponse like() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("save")
    PostResponse saveForLater() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report")
    PostReportResponse report() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report/resolve")
    PostReportResponse reportResolve() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report/list")
    ListPostReportsResponse reportList() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("site_metadata")
    GetSiteMetadataResponse siteMetadata() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
