package com.sublinks.sublinksapi.community;

import com.sublinks.sublinksapi.community.event.CommunityCreatedPublisher;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityAggregateRepository communityAggregateRepository;
    private final CommunityCreatedPublisher communityCreatedPublisher;

    public CommunityService(
            final CommunityRepository communityRepository,
            final CommunityAggregateRepository communityAggregateRepository,
            final CommunityCreatedPublisher communityCreatedPublisher
    ) {
        this.communityRepository = communityRepository;
        this.communityAggregateRepository = communityAggregateRepository;
        this.communityCreatedPublisher = communityCreatedPublisher;
    }

    public void createCommunity(Community community) {

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
