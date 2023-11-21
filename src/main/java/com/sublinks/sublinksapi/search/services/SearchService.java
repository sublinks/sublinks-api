package com.sublinks.sublinksapi.search.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.search.repositories.CommentSearchRepository;
import com.sublinks.sublinksapi.search.repositories.CommunitySearchRepository;
import com.sublinks.sublinksapi.search.repositories.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchService {
    private final PostSearchRepository postRepository;
    private final CommentSearchRepository commentSearchRepository;
    private final CommunitySearchRepository communitySearchRepository;

    public Page<Community> searchCommunity(final String query, final int page, final int pageSize, final Sort sort) {
        return communitySearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }

    public Page<Post> searchPost(final String query, final int page, final int pageSize, final Sort sort) {
        return postRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }

    public Page<Comment> searchComments(final String query, final int page, final int pageSize, final Sort sort) {
        return commentSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }
}
