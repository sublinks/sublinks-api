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
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import com.sublinks.sublinksapi.person.repositories.LinkPersonPersonRepository;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.person.services.LinkPersonPersonService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserInfoService {

  private final ConversionService conversionService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final LinkPersonPersonRepository linkPersonPersonRepository;
  private final LinkPersonPersonService linkPersonPersonService;

  public MyUserInfo getMyUserInfo(com.sublinks.sublinksapi.person.entities.Person person) {

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
      com.sublinks.sublinksapi.person.entities.Person person) {

    // @todo block instances
    Collection<InstanceBlockView> blocked = new ArrayList<>();
    return blocked;
  }

  public Collection<Long> getDiscussionLanguages(
      com.sublinks.sublinksapi.person.entities.Person person) {

    Collection<Long> languages = new ArrayList<>();
    for (Language language : person.getLanguages()) {
      languages.add(language.getId());
    }
    return languages;
  }

  public Collection<CommunityFollowerView> getUserCommunityFollows(
      com.sublinks.sublinksapi.person.entities.Person person) {

    Collection<Community> communities = linkPersonCommunityService.getLinks(person,
            LinkPersonCommunityType.follower)
        .stream()
        .map(LinkPersonCommunity::getCommunity)
        .toList();

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
      com.sublinks.sublinksapi.person.entities.Person person) {

    // @todo make this a single query
    List<Community> moderatingCommunities = linkPersonCommunityService.getLinks(person,
            List.of(LinkPersonCommunityType.owner, LinkPersonCommunityType.moderator))
        .stream()
        .map(LinkPersonCommunity::getCommunity)
        .toList();

    Collection<CommunityModeratorView> communityModeratorViews = new ArrayList<>();
    for (Community community : moderatingCommunities) {
      communityModeratorViews.add(CommunityModeratorView.builder()
          .moderator(conversionService.convert(person, Person.class))
          .community(conversionService.convert(community,
              com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
          .build());
    }

    return communityModeratorViews;
  }

  public Collection<CommunityBlockView> getUserCommunitiesBlocked(
      com.sublinks.sublinksapi.person.entities.Person person) {

    List<Community> communities = linkPersonCommunityService.getLinks(person,
            LinkPersonCommunityType.blocked)
        .stream()
        .map(LinkPersonCommunity::getCommunity)
        .toList();

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
      com.sublinks.sublinksapi.person.entities.Person person) {

    return linkPersonPersonService.getLinksByEntity(person, List.of(LinkPersonPersonType.blocked))
        .stream()
        .map(link -> PersonBlockView.builder()
            .target(conversionService.convert(link.getToPerson(), Person.class))
            .person(conversionService.convert(person, Person.class))
            .build())
        .toList();
  }
}
