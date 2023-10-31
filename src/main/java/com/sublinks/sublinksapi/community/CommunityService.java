package com.sublinks.sublinksapi.community;

import com.sublinks.sublinksapi.community.events.CommunityCreatedPublisher;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
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
