package com.sublinks.sublinksapi.api.lemmy.v3.site.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityFollowerView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.InstanceBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUserView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.MyUserInfo;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonBlockView;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserInfoService {

  private final ConversionService conversionService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  public MyUserInfo getMyUserInfo(com.sublinks.sublinksapi.person.dto.Person person) {

    return MyUserInfo.builder()
        .local_user_view(LocalUserView.builder()
            .local_user(conversionService.convert(person, LocalUser.class))
            .person(conversionService.convert(person, Person.class))
            .counts(conversionService.convert(person.getPersonAggregate(), PersonAggregates.class))
            .build())
        .follows(getUserCommunityFollows(person))
        .moderates(getUserCommunityModerates(person))
        .community_blocks(getUserCommunitiesBlocked(person))
        .instance_blocks(getInstanceBlocked(person))
        .person_blocks(getUserPeopleBlocked(person))
        .discussion_languages(getDiscussionLanguages(person))
        .build();
  }

  public Collection<InstanceBlockView> getInstanceBlocked(
      com.sublinks.sublinksapi.person.dto.Person person) {

    // @todo block instances
    Collection<InstanceBlockView> blocked = new ArrayList<>();
    return blocked;
  }

  public Collection<Long> getDiscussionLanguages(
      com.sublinks.sublinksapi.person.dto.Person person) {

    Collection<Long> languages = new ArrayList<>();
    for (Language language : person.getLanguages()) {
      languages.add(language.getId());
    }
    return languages;
  }

  public Collection<CommunityFollowerView> getUserCommunityFollows(
      com.sublinks.sublinksapi.person.dto.Person person) {

    Collection<Community> communities = linkPersonCommunityService.getPersonLinkByType(person,
        LinkPersonCommunityType.follower);

    Collection<CommunityFollowerView> communityFollowerViews = new ArrayList<>();
    for (Community community : communities) {
      communityFollowerViews.add(CommunityFollowerView.builder()
          .follower(conversionService.convert(person, Person.class))
          .community(conversionService.convert(community,
              com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
          .build());
    }

    return communityFollowerViews;
  }

  public Collection<CommunityModeratorView> getUserCommunityModerates(
      com.sublinks.sublinksapi.person.dto.Person person) {

    // @todo make this a single query
    Collection<Community> communitiesOwned = linkPersonCommunityService.getPersonLinkByType(person,
        LinkPersonCommunityType.owner);
    Collection<Community> communities = linkPersonCommunityService.getPersonLinkByType(person,
        LinkPersonCommunityType.moderator);

    Collection<CommunityModeratorView> communityModeratorViews = new ArrayList<>();
    for (Community community : communitiesOwned) {
      communityModeratorViews.add(
          CommunityModeratorView.builder()
              .moderator(conversionService.convert(person, Person.class))
              .community(conversionService.convert(community,
                  com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
              .build());
    }
    for (Community community : communities) {
      communityModeratorViews.add(
          CommunityModeratorView.builder()
              .moderator(conversionService.convert(person, Person.class))
              .community(conversionService.convert(community,
                  com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
              .build());
    }
    return communityModeratorViews;
  }

  public Collection<CommunityBlockView> getUserCommunitiesBlocked(
      com.sublinks.sublinksapi.person.dto.Person person) {

    Collection<Community> communities = linkPersonCommunityService.getPersonLinkByType(person,
        LinkPersonCommunityType.blocked);

    Collection<CommunityBlockView> communityBlockViews = new ArrayList<>();
    for (Community community : communities) {
      communityBlockViews.add(CommunityBlockView.builder()
          .person(conversionService.convert(person, Person.class))
          .community(conversionService.convert(community,
              com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
          .build());
    }
    return communityBlockViews;
  }

  public Collection<PersonBlockView> getUserPeopleBlocked(
      com.sublinks.sublinksapi.person.dto.Person person) {

    return new ArrayList<>(); // @todo people blocks
  }
}
