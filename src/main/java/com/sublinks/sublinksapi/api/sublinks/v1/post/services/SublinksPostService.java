package com.sublinks.sublinksapi.api.sublinks.v1.post.services;

import com.sublinks.sublinksapi.api.sublinks.v1.post.models.CreatePost;
import com.sublinks.sublinksapi.api.sublinks.v1.post.models.IndexPost;
import com.sublinks.sublinksapi.api.sublinks.v1.post.models.PostResponse;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPostTypes;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.Post.PostBuilder;
import com.sublinks.sublinksapi.post.entities.PostReport;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostReportService;
import com.sublinks.sublinksapi.post.services.PostService;
import com.sublinks.sublinksapi.shared.RemovedState;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterBlockedException;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterReportException;
import com.sublinks.sublinksapi.slurfilter.services.SlurFilterService;
import com.sublinks.sublinksapi.utils.SiteMetadataUtil;
import com.sublinks.sublinksapi.utils.UrlUtil;
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
  private final Instance localInstance;
  private final SlurFilterService slurFilterService;
  private final UrlUtil urlUtil;
  private final SiteMetadataUtil siteMetadataUtil;
  private final PostReportService postReportService;
  private final PersonService personService;

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

  /**
   * Retrieves the PostResponse object for the given key and person.
   *
   * @param key    The key of the post.
   * @param person The person object representing the user.
   * @return The PostResponse object matching the key.
   * @throws ResponseStatusException If the post is not found or the user does not have permission
   *                                 to access the post.
   */
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

  /**
   * Creates a new Post with the provided form data and person.
   *
   * @param createPostForm The CreatePost object containing the form data for creating the post.
   * @param person         The Person object representing the user who is creating the post.
   * @return The created PostResponse object.
   * @throws ResponseStatusException If the community or feature post permission is not found or
   *                                 denied.
   */
  public PostResponse create(final CreatePost createPostForm, final Person person) {

    final Community community = communityRepository.findCommunityByTitleSlug(
            createPostForm.communityKey())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "community_not_found"));

    final Language language = createPostForm.languageKey() != null && !createPostForm.languageKey()
        .isEmpty() ? languageRepository.findLanguageByCode(createPostForm.languageKey())
        : personService.getPersonDefaultPostLanguage(person, community)
            .orElse(languageRepository.findLanguageByCode("und"));

    if (language == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found");
    }

    if (community.getLanguages()
        .stream()
        .noneMatch(communityLanguage -> communityLanguage.getCode()
            .equals(language.getCode()))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "language_not_supported_by_community");
    }

    if (community.getInstance()
        .getLanguages()
        .stream()
        .noneMatch(instanceLanguage -> instanceLanguage.getCode()
            .equals(language.getCode()))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "language_not_supported_by_instance");
    }

    languageRepository.findLanguageByCode("und");

    final PostBuilder postBuilder = Post.builder()
        .title(createPostForm.title())
        .postBody(createPostForm.body())
        .language(language)
        .community(community)
        .isNsfw(createPostForm.nsfw())
        .isFeatured(createPostForm.featuredLocal())
        .isFeaturedInCommunity(createPostForm.featuredCommunity())
        .instance(localInstance)
        .removedState(RemovedState.NOT_REMOVED);

    boolean shouldReport = false;

    try {
      postBuilder.postBody(slurFilterService.censorText(createPostForm.body()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      postBuilder.postBody(createPostForm.body());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_blocked_by_slur_filter");
    }

    try {
      postBuilder.title(slurFilterService.censorText(createPostForm.title()));
    } catch (SlurFilterReportException e) {
      shouldReport = true;
      postBuilder.title(createPostForm.title());
    } catch (SlurFilterBlockedException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_blocked_by_slur_filter");
    }
    String url = createPostForm.link();
    SiteMetadataUtil.SiteMetadata metadata = null;
    if (url != null) {
      String metadataUrl = urlUtil.normalizeUrl(url);
      urlUtil.checkUrlProtocol(metadataUrl);
      metadata = siteMetadataUtil.fetchSiteMetadata(metadataUrl);
    }

    if (url != null) {
      postBuilder.linkUrl(url);
      if (metadata != null) {
        postBuilder.linkTitle(metadata.title())
            .linkDescription(metadata.description())
            .linkVideoUrl(metadata.videoUrl())
            .linkThumbnailUrl(metadata.imageUrl());
      }
    }

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

    if (shouldReport) {
      postReportService.createPostReport(PostReport.builder()
          .post(post)
          .creator(person)
          .reason("AUTOMATED: Post creation triggered a slur filter")
          .originalBody(post.getPostBody() == null ? "" : post.getPostBody())
          .originalTitle(post.getTitle() == null ? "" : post.getTitle())
          .originalUrl(post.getLinkUrl() == null ? "" : post.getLinkUrl())
          .build());
    }

    return conversionService.convert(post, PostResponse.class);
  }

}
