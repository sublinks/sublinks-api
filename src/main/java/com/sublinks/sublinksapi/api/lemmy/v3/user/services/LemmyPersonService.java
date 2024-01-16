package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentService;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPersonService {

  private final ConversionService conversionService;
  private final RoleAuthorizingService roleAuthorizingService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final LemmyPostService lemmyPostService;
  private final LemmyCommentService lemmyCommentService;
  private final PostRepository postRepository;

  public PersonView getPersonView(Person person) {

    final boolean is_admin = RoleAuthorizingService.isAdmin(person);
    return PersonView.builder()
        .person(conversionService.convert(person,
            com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
        .counts(conversionService.convert(person.getPersonAggregate(), PersonAggregates.class))
        .is_admin(is_admin)
        .build();
  }

  public Collection<PostView> getPersonPosts(Person person) {

    Collection<PostView> postViews = new ArrayList<>();

    postRepository.allPostsByPerson(person).forEach(post -> {
      postViews.add(lemmyPostService.postViewFromPost(post, person));
    });

    return postViews;
  }

  public Collection<CommunityModeratorView> getPersonModerates(Person person) {

    Collection<CommunityModeratorView> communityModeratorViews = new ArrayList<>();
    linkPersonCommunityService.getPersonLinkByType(person, LinkPersonCommunityType.moderator)
        .forEach(community -> {
          communityModeratorViews.add(CommunityModeratorView.builder().community(
                  conversionService.convert(community,
                      com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
              .moderator(conversionService.convert(person,
                  com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
              .build());
        });
    return communityModeratorViews;
  }

  public Collection<CommentView> getPersonComments(Person person) {

    Collection<CommentView> commentViews = new ArrayList<>();

//    person.getComments().forEach(
//            comment -> commentViews.add(lemmyCommentService.createCommentView(comment, person)));

    return commentViews;
  }
}
