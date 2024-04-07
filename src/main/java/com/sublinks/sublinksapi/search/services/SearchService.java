package com.sublinks.sublinksapi.search.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.CrossPost;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.repositories.CrossPostRepository;
import com.sublinks.sublinksapi.post.services.PostService;
import com.sublinks.sublinksapi.search.repositories.CommentSearchRepository;
import com.sublinks.sublinksapi.search.repositories.CommunitySearchRepository;
import com.sublinks.sublinksapi.search.repositories.PersonSearchRepository;
import com.sublinks.sublinksapi.search.repositories.PostSearchRepository;
import com.sublinks.sublinksapi.utils.UrlUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchService {
    private final PostSearchRepository postSearchRepository;
    private final CommentSearchRepository commentSearchRepository;
    private final CommunitySearchRepository communitySearchRepository;
    private final PersonSearchRepository personSearchRepository;
    private final PostService postService;
    private final CrossPostRepository crossPostRepository;
    private final UrlUtil urlUtil;

    public Page<Community> searchCommunity(final String query, final int page, final int pageSize, final Sort sort) {
        return communitySearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }

    public Page<Post> searchPost(final String query, final int page, final int pageSize, final Sort sort) {
        return postSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }

    public Page<Comment> searchComments(final String query, final int page, final int pageSize, final Sort sort) {
        return commentSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }

    public Page<Post> searchPostByUrl(final String url, final int page, final int pageSize, final Sort sort) {
        String cleanUrl = urlUtil.normalizeUrl(url);
        String hash = postService.getStringMd5Hash(cleanUrl);
        Optional<CrossPost> crossPost = crossPostRepository.getCrossPostByMd5Hash(hash);

        if(crossPost.isEmpty()){
            return Page.empty();
        }
        List<Post> posts = crossPost.get().getPosts().stream().toList();
        return new PageImpl<>(posts.subList(page * pageSize, (page + 1) * pageSize),
                PageRequest.of(page, pageSize, sort), posts.size());
    }

    public Page<Person> searchPerson(final String query, final int page, final int pageSize, final Sort sort) {
        return personSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort));
    }
}
