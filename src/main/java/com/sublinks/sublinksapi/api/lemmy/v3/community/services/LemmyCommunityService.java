package com.sublinks.sublinksapi.api.lemmy.v3.community.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.entities.CommunityAggregate;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyCommunityService {

  private final ConversionService conversionService;

  public SubscribedType getPersonCommunitySubscribeType(final Person person,
      final Community community) {

    if (person == null || community == null) {
      return SubscribedType.NotSubscribed;
    }
    for (LinkPersonCommunity link : person.getLinkPersonCommunity()) {
      if (link.getCommunity() == community) {
        return switch (link.getLinkType()) {
          case owner, follower, moderator -> SubscribedType.Subscribed;
          case pending_follow -> SubscribedType.Pending;
          default -> SubscribedType.NotSubscribed;
        };
      }
    }
    return SubscribedType.NotSubscribed;
  }

  public CommunityView communityViewFromCommunity(final Community community) {

    return CommunityView.builder()
        .community(conversionService.convert(community,
            com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
        .subscribed(SubscribedType.NotSubscribed)
        .blocked(false)
        .counts(
            conversionService.convert(communityAggregates(community), CommunityAggregates.class))
        .build();
  }

  public CommunityView communityViewFromCommunity(final Community community, final Person person) {

    SubscribedType subscribedType = SubscribedType.NotSubscribed;
    boolean isBlocked = false;
    for (LinkPersonCommunity linkPersonCommunity : person.getLinkPersonCommunity()) {
      if (Objects.equals(community.getId(), linkPersonCommunity.getCommunity().getId())) {
        if (linkPersonCommunity.getLinkType() == LinkPersonCommunityType.follower) {
          subscribedType = SubscribedType.Subscribed;
        } else if (linkPersonCommunity.getLinkType() == LinkPersonCommunityType.pending_follow) {
          subscribedType = SubscribedType.Pending;
        }
        if (linkPersonCommunity.getLinkType() == LinkPersonCommunityType.blocked) {
          isBlocked = true;
        }
      }
    }
    return CommunityView.builder()
        .community(conversionService.convert(community,
            com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
        .subscribed(subscribedType)
        .blocked(isBlocked)
        .counts(
            conversionService.convert(communityAggregates(community), CommunityAggregates.class))
        .build();
  }

  public CommunityAggregate communityAggregates(final Community community) {

    return Optional.ofNullable(community.getCommunityAggregate())
        .orElse(CommunityAggregate.builder().community(community).build());
  }

  public List<Long> communityLanguageCodes(final Community community) {

    final List<Long> languageCodes = new ArrayList<>();
    for (Language language : community.getLanguages()) {
      languageCodes.add(language.getId());
    }
    return languageCodes;
  }

  public List<CommunityModeratorView> communityModeratorViewList(final Community community) {

    final List<CommunityModeratorView> moderatorViews = new ArrayList<>();
    for (LinkPersonCommunity linkPerson : community.getLinkPersonCommunity()) {
      final CommunityModeratorView communityModeratorView = CommunityModeratorView.builder()
          .community(conversionService.convert(
              community,
              com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class)
          )
          .moderator(conversionService.convert(
              linkPerson.getPerson(),
              com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class)
          ).build();
      if (linkPerson.getLinkType() == LinkPersonCommunityType.owner) {
        moderatorViews.add(0, communityModeratorView);
      }
      if (linkPerson.getLinkType() == LinkPersonCommunityType.moderator) {
        moderatorViews.add(communityModeratorView);
      }
    }
    return moderatorViews;
  }

  public CommunityResponse createCommunityResponse(final Community community, final Person person) {

    return CommunityResponse.builder()
        .community_view(communityViewFromCommunity(community, person))
        .discussion_languages(communityLanguageCodes(community))
        .build();
  }

  public CommunityResponse createCommunityResponse(final Community community) {

    return CommunityResponse.builder()
        .community_view(communityViewFromCommunity(community))
        .discussion_languages(communityLanguageCodes(community))
        .build();
  }
}
