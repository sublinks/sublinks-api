package com.sublinks.sublinksapi.community;

import com.sublinks.sublinksapi.community.event.CommunityCreatedPublisher;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityCreatedPublisher communityCreatedPublisher;

    public CommunityService(
            CommunityRepository communityRepository,
            CommunityCreatedPublisher communityCreatedPublisher
    ) {
        this.communityRepository = communityRepository;
        this.communityCreatedPublisher = communityCreatedPublisher;
    }

    public void saveCommunity(Community community) {

        communityRepository.save(community);
        communityCreatedPublisher.publish(community);
    }
}
