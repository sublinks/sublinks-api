package com.sublinks.sublinksapi.community;

import com.sublinks.sublinksapi.community.events.CommunityCreatedPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityAggregateRepository communityAggregateRepository;
    private final CommunityCreatedPublisher communityCreatedPublisher;

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
