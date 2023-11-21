package com.sublinks.sublinksapi.api.lemmy.v3.search.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SearchType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.api.lemmy.v3.search.models.Search;
import com.sublinks.sublinksapi.api.lemmy.v3.search.models.SearchResponse;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.search.services.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/search")
public class SearchController extends AbstractLemmyApiController {
    private final SearchService searchService;
    private final LemmyCommunityService lemmyCommunityService;
    private final LemmyCommentService lemmyCommentService;
    private final LemmyPostService lemmyPostService;

    @GetMapping
    SearchResponse search(@Valid final Search searchForm) {

        final SearchResponse.SearchResponseBuilder responseBuilder = SearchResponse.builder();

        final int page = searchForm.page() == null ? 0 : Integer.parseInt(searchForm.page());
        final int pLimit = Integer.parseInt(searchForm.limit());
        final int limit = Math.min(pLimit, 25);
        if(searchForm.type_() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Sort sort = switch (searchForm.sort()) {
            case "New" -> Sort.by("createdAt").descending();
            case "Old" -> Sort.by("createdAt").ascending();
            default -> Sort.unsorted();
        };

        final boolean isAll = searchForm.type_() == SearchType.All;

        if(searchForm.type_() == SearchType.Comments || isAll)
        {
            List<Comment> comments = searchService.searchComments(searchForm.q(), page, limit, sort).getContent();
            List<CommentView> commentViewList = comments.stream().map(lemmyCommentService::createCommentView).toList();
            
            responseBuilder.comments(commentViewList);
        }
        if(searchForm.type_() == SearchType.Posts || isAll)
        {
            List<Post> posts = searchService.searchPost(searchForm.q(), page, limit, sort).getContent();
            List<PostView> commentViewList = posts.stream().map(lemmyPostService::postViewFromPost).toList();

            responseBuilder.posts(commentViewList);
        }
        if(searchForm.type_() == SearchType.Communities || isAll)
        {
            List<Community> communities = searchService.searchCommunity(searchForm.q(), page, limit, sort).getContent();
            List<CommunityView> commentViewList = communities.stream().map(lemmyCommunityService::communityViewFromCommunity).toList();

            responseBuilder.communities(commentViewList);
        }

        return responseBuilder.build();
    }
}
