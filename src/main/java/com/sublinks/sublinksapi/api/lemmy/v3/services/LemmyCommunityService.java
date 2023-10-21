package com.sublinks.sublinksapi.api.lemmy.v3.services;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.community.CommunityModeratorViewMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.community.CommunityAggregates;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class LemmyCommunityService {
    final private LemmyCommunityMapper lemmyCommunityMapper;

    private final CommunityModeratorViewMapper communityModeratorViewMapper;

    public LemmyCommunityService(LemmyCommunityMapper lemmyCommunityMapper, CommunityModeratorViewMapper communityModeratorViewMapper) {
        this.lemmyCommunityMapper = lemmyCommunityMapper;
        this.communityModeratorViewMapper = communityModeratorViewMapper;
    }

    public SubscribedType getPersonCommunitySubscribeType(Person person, Community community) {
        for (LinkPersonCommunity link :
                person.getLinkPersonCommunity()) {
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

    public CommunityView communityViewFromCommunity(Community community) {
        CommunityAggregates communityAggregates = communityAggregates(community);
        return lemmyCommunityMapper.communityToCommunityView(
                community,
                SubscribedType.NotSubscribed,
                false,
                communityAggregates
        );
    }

    public CommunityView communityViewFromCommunity(Community community, Person person) {
        SubscribedType subscribedType = SubscribedType.NotSubscribed;
        boolean isBlocked = false;
        for (LinkPersonCommunity linkPersonCommunity : person.getLinkPersonCommunity()) {
            if (Objects.equals(community.getId(), linkPersonCommunity.getCommunity().getId())) {
                subscribedType = switch (linkPersonCommunity.getLinkType()) {
                    case owner, follower, moderator -> SubscribedType.Subscribed;
                    case pending_follow -> SubscribedType.Pending;
                    default -> SubscribedType.NotSubscribed;
                };
                if (linkPersonCommunity.getLinkType() == LinkPersonCommunityType.blocked) {
                    isBlocked = true;
                }
            }
        }
        CommunityAggregates communityAggregates = communityAggregates(community);
        return lemmyCommunityMapper.communityToCommunityView(
                community,
                subscribedType,
                isBlocked,
                communityAggregates
        );
    }

    public CommunityAggregates communityAggregates(Community community) {
        return Optional.ofNullable(community.getCommunityAggregates())
                .orElse(CommunityAggregates.builder().community(community).build());
    }

    public Set<String> communityLanguageCodes(Community community) {
        Set<String> languageCodes = new HashSet<>();
        for (Language language : community.getLanguages()) {
            languageCodes.add(language.getCode());
        }
        return languageCodes;
    }

    public List<CommunityModeratorView> communityModeratorViewList(Community community) {
        List<CommunityModeratorView> moderatorViews = new ArrayList<>();
        for (LinkPersonCommunity linkPerson : community.getLinkPersonCommunity()) {
            final CommunityModeratorView communityModeratorView = communityModeratorViewMapper.map(community, linkPerson.getPerson());
            if (linkPerson.getLinkType() == LinkPersonCommunityType.owner) {
                moderatorViews.add(0, communityModeratorView);
            }
            if (linkPerson.getLinkType() == LinkPersonCommunityType.moderator) {
                moderatorViews.add(communityModeratorView);
            }
        }
        return moderatorViews;
    }
}
