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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SearchServiceTests {
  @Mock
  private  PostSearchRepository postSearchRepository;

  @Mock
  private  CommentSearchRepository commentSearchRepository;

  @Mock
  private  CommunitySearchRepository communitySearchRepository;

  @Mock
  private  PersonSearchRepository personSearchRepository;

  @Mock
  private  PostService postService;

  @Mock
  private  CrossPostRepository crossPostRepository;

  @Mock
  private  UrlUtil urlUtil;

  @InjectMocks
  private SearchService searchService;

  @BeforeEach
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testSearchCommunity(){
    String query = "test";
    int page = 0;
    int pageSize = 10;
    Sort sort = Sort.by("id");

    Page<Community> expectedPage = new PageImpl<>(
        Collections.emptyList(), PageRequest.of(page, pageSize, sort), 0);
    when(communitySearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort)))
        .thenReturn(expectedPage);

    Page<Community> result = searchService.searchCommunity(query, page, pageSize, sort);
    assertEquals(expectedPage, result);
  }
  @Test
  public void testSearchPost() {
    String query = "test";
    int page = 0;
    int pageSize = 10;
    Sort sort = Sort.by("id");

    Page<Post> expectedPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, pageSize, sort), 0);
    when(postSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort)))
        .thenReturn(expectedPage);

    Page<Post> result = searchService.searchPost(query, page, pageSize, sort);
    assertEquals(expectedPage, result);
  }

  @Test
  public void testSearchComments() {
    String query = "test";
    int page = 0;
    int pageSize = 10;
    Sort sort = Sort.by("id");

    Page<Comment> expectedPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, pageSize, sort), 0);
    when(commentSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort)))
        .thenReturn(expectedPage);

    Page<Comment> result = searchService.searchComments(query, page, pageSize, sort);
    assertEquals(expectedPage, result);
  }

  @Test
  public void testSearchPostByUrl() {
    String url = "http://example.com";
    int page = 0;
    int pageSize = 2;
    Sort sort = Sort.by("id");

    String cleanUrl = "http://example.com/clean";
    String hash = "hashed-url";
    Post post1 = new Post();
    Post post2 = new Post();
    Set<Post> posts = Set.of(post1,post2);
    CrossPost crossPost = new CrossPost();
    crossPost.setPosts(posts);
    when(urlUtil.normalizeUrl(url)).thenReturn(cleanUrl);
    when(postService.getStringMd5Hash(cleanUrl)).thenReturn(hash);
    when(crossPostRepository.getCrossPostByMd5Hash(hash)).thenReturn(Optional.of(crossPost));

    Page<Post> expectedPage = new PageImpl<>(posts.stream().toList(),
        PageRequest.of(page, pageSize, sort), posts.size());

    Page<Post> result = searchService.searchPostByUrl(url, page, pageSize, sort);

    assertEquals(expectedPage, result);
  }

  @Test
  public void testSearchPerson() {
    String query = "test";
    int page = 0;
    int pageSize = 10;
    Sort sort = Sort.by("id");

    Page<Person> expectedPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, pageSize, sort), 0);
    when(personSearchRepository.searchAllByKeyword(query, PageRequest.of(page, pageSize, sort)))
        .thenReturn(expectedPage);

    Page<Person> result = searchService.searchPerson(query, page, pageSize, sort);
    assertEquals(expectedPage, result);
  }

}
