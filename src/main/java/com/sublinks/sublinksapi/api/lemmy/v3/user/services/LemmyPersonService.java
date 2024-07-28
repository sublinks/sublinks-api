package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetails;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LemmyPersonService {

  private final ConversionService conversionService;
  private final RolePermissionService rolePermissionService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final LemmyPostService lemmyPostService;
  private final LemmyCommentService lemmyCommentService;
  private final PostRepository postRepository;
  private final CommunityRepository communityRepository;
  private final CommentRepository commentRepository;

  public PersonView getPersonView(Person person) {

    final boolean is_admin = RolePermissionService.isAdmin(person);
    return PersonView.builder()
        .person(conversionService.convert(person,
            com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
        .counts(conversionService.convert(person.getPersonAggregate(), PersonAggregates.class))
        .is_admin(is_admin)
        .build();
  }

  public Collection<PostView> getPersonPosts(final Person person,
      final GetPersonDetails getPersonDetails) {

    Collection<PostView> postViews = new ArrayList<>();

    final PostSearchCriteria postSearchCriteria = PostSearchCriteria.builder()
        .person(person)
        .isSavedOnly(getPersonDetails.saved_only() != null && getPersonDetails.saved_only())
        .sortType(conversionService.convert(getPersonDetails.sort(), SortType.class))
        .listingType(ListingType.All)
        .perPage(getPersonDetails.limit())
        .communityIds(getPersonDetails.community_id() != null ? List.of(
            getPersonDetails.community_id()
                .longValue()) : null)
        .page(getPersonDetails.page())
        .build();

    postRepository.allPostsBySearchCriteria(postSearchCriteria)
        .forEach(post -> postViews.add(lemmyPostService.postViewFromPost(post, person)));

    return postViews;
  }

  public Collection<CommunityModeratorView> getPersonModerates(Person person) {

    Collection<CommunityModeratorView> communityModeratorViews = new ArrayList<>();
    linkPersonCommunityService.getLinks(person, LinkPersonCommunityType.moderator)
        .forEach(community -> {
          communityModeratorViews.add(CommunityModeratorView.builder()
              .community(conversionService.convert(community,
                  com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
              .moderator(conversionService.convert(person,
                  com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
              .build());
        });
    return communityModeratorViews;
  }

  @Transactional
  public Collection<CommentView> getPersonComments(Person person,
      final GetPersonDetails getPersonDetails) {

    Collection<CommentView> commentViews = new ArrayList<>();

    final Optional<com.sublinks.sublinksapi.community.entities.Community> community =
        getPersonDetails.community_id() != null ? communityRepository.findById(
            (long) getPersonDetails.community_id()) : Optional.empty();

    final CommentSearchCriteria commentSearchCriteria = CommentSearchCriteria.builder()
        .person(person)
        .commentSortType(conversionService.convert(getPersonDetails.sort(), CommentSortType.class))
        .listingType(ListingType.All)
        .savedOnly(getPersonDetails.saved_only() != null && getPersonDetails.saved_only())
        .community(community.orElse(null))
        .perPage(getPersonDetails.limit())
        .page(getPersonDetails.page())
        .build();

    commentRepository.allCommentsBySearchCriteria(commentSearchCriteria)
        .forEach(
            comment -> commentViews.add(lemmyCommentService.createCommentView(comment, person)));

    return commentViews;
  }
}
