package com.sublinks.sublinksapi.community.services;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.dto.CommunityAggregate;
import com.sublinks.sublinksapi.community.events.CommunityCreatedPublisher;
import com.sublinks.sublinksapi.community.repositories.CommunityAggregateRepository;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityAggregateRepository communityAggregateRepository;
    private final CommunityCreatedPublisher communityCreatedPublisher;
    private final LocalInstanceContext localInstanceContext;

    public void createCommunity(Community community) {

        community.setActivityPubId(localInstanceContext.instance().getDomain() + "/" + community.getTitleSlug());
        communityRepository.save(community);
        final CommunityAggregate communityAggregate = CommunityAggregate.builder()
                .community(community)
                .subscriberCount(1)
                .build();
        communityAggregateRepository.save(communityAggregate);
        community.setCommunityAggregate(communityAggregate);
        communityCreatedPublisher.publish(community);
    }
}
