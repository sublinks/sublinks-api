package com.sublinks.sublinksapi.search.services;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.CrossPost;
import com.sublinks.sublinksapi.post.entities.Post;
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

  /**
   * Searches for communities based on a given query.
   *
   * @param query    The keyword to search for.
   * @param page     The page number of the search results.
   * @param pageSize The number of results per page.
   * @param sort     The sorting criteria for the search results.
   * @return A Page object containing the search results. If no results are found, an empty Page
   * will be returned.
   */
  public Page<Community> searchCommunity(final String query, final int page, final int pageSize,
      final Sort sort) {

    final int realPage = page <= 0 ? 1 : page;
    final int realPageSize = pageSize <= 0 ? 1 : pageSize;

    return communitySearchRepository.searchAllByKeyword(query,
        PageRequest.of(realPage, realPageSize, sort));
  }

  /**
   * Searches for posts based on a given query.
   *
   * @param query    The keyword to search for.
   * @param page     The page number of the search results.
   * @param pageSize The number of results per page.
   * @param sort     The sorting criteria for the search results.
   * @return A Page object containing the search results. If no results are found, an empty Page will be returned.
   */
  public Page<Post> searchPost(final String query, final int page, final int pageSize,
      final Sort sort) {

    final int realPage = page <= 0 ? 1 : page;
    final int realPageSize = pageSize <= 0 ? 1 : pageSize;

    return postSearchRepository.searchAllByKeyword(query,
        PageRequest.of(realPage, realPageSize, sort));
  }

  /**
   * Searches for comments based on a given query.
   *
   * @param query    The keyword to search for.
   * @param page     The page number of the search results.
   * @param pageSize The number of results per page.
   * @param sort     The sorting criteria for the search results.
   * @return A Page object containing the search results. If no results are found, an empty Page
   * will be returned.
   */
  public Page<Comment> searchComments(final String query, final int page, final int pageSize,
      final Sort sort) {

    final int realPage = page <= 0 ? 1 : page;
    final int realPageSize = pageSize <= 0 ? 1 : pageSize;

    return commentSearchRepository.searchAllByKeyword(query,
        PageRequest.of(realPage, realPageSize, sort));
  }

  /**
   * Searches for a post by URL.
   *
   * @param url      The URL to search for.
   * @param page     The page number of the search results.
   * @param pageSize The number of results per page.
   * @param sort     The sorting criteria for the search results.
   * @return A Page object containing the search results. If no results are found, an empty Page
   * will be returned.
   */
  public Page<Post> searchPostByUrl(final String url, final int page, final int pageSize,
      final Sort sort) {

    String cleanUrl = urlUtil.normalizeUrl(url);
    String hash = postService.getStringMd5Hash(cleanUrl);
    Optional<CrossPost> crossPost = crossPostRepository.getCrossPostByMd5Hash(hash);

    if (crossPost.isEmpty()) {
      return Page.empty();
    }
    List<Post> posts = crossPost.get()
        .getPosts()
        .stream()
        .toList();

    final int realPage = page <= 0 ? 1 : page;
    final int realPageSize = pageSize <= 0 ? 1 : pageSize;

    return new PageImpl<>(posts.subList(realPage * realPageSize, (realPage + 1) * realPageSize),
        PageRequest.of(realPage, realPageSize, sort), posts.size());
  }

  /**
   * Searches for persons based on a given query.
   *
   * @param query    The keyword to search for.
   * @param page     The page number of the search results.
   * @param pageSize The number of results per page.
   * @param sort     The sorting criteria for the search results.
   * @return A Page object containing the search results. If no results are found, an empty Page
   * will be returned.
   */
  public Page<Person> searchPerson(final String query, final int page, final int pageSize,
      final Sort sort) {

    final int realPage = page <= 0 ? 1 : page;
    final int realPageSize = pageSize <= 0 ? 1 : pageSize;
    return personSearchRepository.searchAllByKeyword(query,
        PageRequest.of(realPage, realPageSize, sort));
  }
}
