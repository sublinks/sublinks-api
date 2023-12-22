package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.person.dto.Person;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPersonService {

  private final ConversionService conversionService;
  private final AuthorizationService authorizationService;

  public PersonView getPersonView(Person person) {

    final boolean is_admin = authorizationService.isAdmin(person);
    return PersonView.builder()
        .person(conversionService.convert(person,
            com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class))
        .counts(conversionService.convert(person.getPersonAggregate(), PersonAggregates.class))
        .is_admin(is_admin)
        .build();
  }

  public Collection<PostView> getPersonPosts(Person person) {

    Collection<PostView> postViews = new ArrayList<>();
    // @todo person posts
    return postViews;
  }

  public Collection<CommunityModeratorView> getPersonModerates(Person person) {

    Collection<CommunityModeratorView> communityModeratorViews = new ArrayList<>();
    // @todo communities moderated
    return communityModeratorViews;
  }

  public Collection<CommentView> getPersonComments(Person person) {

    Collection<CommentView> commentViews = new ArrayList<>();
    // @todo comment view
    return commentViews;
  }
}
