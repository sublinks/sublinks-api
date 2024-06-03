package com.sublinks.sublinksapi.api.sublinks.v1.post.services;

import com.sublinks.sublinksapi.api.sublinks.v1.post.models.CreatePost;
import com.sublinks.sublinksapi.api.sublinks.v1.post.models.IndexPost;
import com.sublinks.sublinksapi.api.sublinks.v1.post.models.PostResponse;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPostTypes;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.Post.PostBuilder;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class SublinksPostService {

  private final PostService postService;
  private final PostRepository postRepository;
  private final ConversionService conversionService;
  private final CommunityRepository communityRepository;
  private final RolePermissionService rolePermissionService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final LanguageRepository languageRepository;

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
        .sortType(conversionService.convert(indexPostForm.sortType(), SortType.class))
        .listingType(conversionService.convert(indexPostForm.listingType(), ListingType.class))
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

  public PostResponse show(final String key, final Person person) {

    final Post post = postRepository.findByTitleSlug(key)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post_not_found"));

    if (post.isRemoved() || post.isDeleted()) {

      final boolean isPermittedToReadRemovedPosts =
          person != null && (rolePermissionService.isPermitted(person,
              RolePermissionPostTypes.ADMIN_SHOW_DELETED_POST) || (
              rolePermissionService.isPermitted(person,
                  RolePermissionPostTypes.MODERATOR_SHOW_DELETED_POST)
                  && linkPersonCommunityService.hasAnyLink(person, post.getCommunity(),
                  List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))));

      final boolean isOwner = person != null && postService.getPostCreator(post) == person;

      if (!isPermittedToReadRemovedPosts || !(isOwner && !post.isRemoved())) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post_not_found");
      }
    }

    return conversionService.convert(post, PostResponse.class);
  }

  public PostResponse create(final CreatePost createPostForm, final Person person) {

    final Language language = createPostForm.languageKey() != null && !createPostForm.languageKey()
        .isEmpty() ? languageRepository.findLanguageByCode(createPostForm.languageKey())
        : languageRepository.findLanguageByCode("und");

    final Community community = communityRepository.findCommunityByTitleSlug(
            createPostForm.communityKey())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "community_not_found"));

    final PostBuilder postBuilder = Post.builder()
        .title(createPostForm.title())
        .postBody(createPostForm.body())
        .language(language)
        .community(community)
        .isNsfw(createPostForm.nsfw())
        .isFeatured(createPostForm.featuredLocal())
        .isFeaturedInCommunity(createPostForm.featuredCommunity());

    if (createPostForm.featuredLocal()) {
      if (!rolePermissionService.isPermitted(person, RolePermissionPostTypes.ADMIN_PIN_POST)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "feature_post_permission_denied");
      }
    }
    if (createPostForm.featuredCommunity()) {
      if (!(rolePermissionService.isPermitted(person, RolePermissionPostTypes.MODERATOR_PIN_POST)
          && linkPersonCommunityService.hasAnyLink(person, community,
          List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner)))
          && !rolePermissionService.isPermitted(person, RolePermissionPostTypes.ADMIN_PIN_POST)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "feature_post_permission_denied");
      }
    }

    final Post post = postBuilder.build();
    postService.createPost(post, person);

    return conversionService.convert(post, PostResponse.class);
  }

}
