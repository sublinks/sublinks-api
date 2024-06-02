package com.sublinks.sublinksapi.api.sublinks.v1.post.services;

import com.sublinks.sublinksapi.api.sublinks.v1.post.models.IndexPost;
import com.sublinks.sublinksapi.api.sublinks.v1.post.models.PostResponse;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SublinksPostService {

  private final PostService postService;
  private final PostRepository postRepository;
  private final ConversionService conversionService;
  private final CommunityRepository communityRepository;

  /**
   * Retrieves a list of PostResponse objects based on the provided search criteria.
   *
   * @param indexPostForm The IndexPost object containing the search criteria.
   * @param person        The Person object representing the user.
   * @return A list of PostResponse objects matching the search criteria.
   */
  public List<PostResponse> index(final IndexPost indexPostForm, final Person person) {

    final List<Community> communities = indexPostForm.communityKeys() == null ? null
        : communityRepository.findCommunityByTitleSlugIn(indexPostForm.communityKeys());

    List<Post> posts = postRepository.allPostsBySearchCriteria(PostSearchCriteria.builder()
        .search(indexPostForm.search())
        .sortType(conversionService.convert(indexPostForm.sortType(),
            com.sublinks.sublinksapi.person.enums.SortType.class))
        .listingType(conversionService.convert(indexPostForm.listingType(),
            com.sublinks.sublinksapi.person.enums.ListingType.class))
        .communityIds(communities == null ? null : communities.stream()
            .map(Community::getId)
            .toList())
        .isShowNsfw(indexPostForm.showNsfw())
        .isSavedOnly(indexPostForm.savedOnly())
        .perPage(indexPostForm.perPage())
        .page(indexPostForm.page())
        .person(person)
        .cursorBasedPageable(indexPostForm.pageCursor())
        .build());

    return posts.stream()
        .map(post -> conversionService.convert(post, PostResponse.class))
        .toList();
  }

}
