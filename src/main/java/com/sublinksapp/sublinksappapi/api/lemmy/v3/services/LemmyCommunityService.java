package com.sublinksapp.sublinksappapi.api.lemmy.v3.services;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.community.CommunityModeratorViewMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityView;
import com.sublinksapp.sublinksappapi.community.Community;
import com.sublinksapp.sublinksappapi.community.CommunityAggregates;
import com.sublinksapp.sublinksappapi.language.Language;
import com.sublinksapp.sublinksappapi.person.LinkPersonCommunity;
import com.sublinksapp.sublinksappapi.person.Person;
import com.sublinksapp.sublinksappapi.person.enums.LinkPersonCommunityType;
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
