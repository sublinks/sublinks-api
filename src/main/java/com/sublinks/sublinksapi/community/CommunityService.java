package com.sublinks.sublinksapi.community;

import com.sublinks.sublinksapi.community.event.CommunityCreatedPublisher;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    final private CommunityRepository communityRepository;
    final private CommunityCreatedPublisher communityCreatedPublisher;

    public CommunityService(
            CommunityRepository communityRepository,
            CommunityCreatedPublisher communityCreatedPublisher
    ) {
        this.communityRepository = communityRepository;
        this.communityCreatedPublisher = communityCreatedPublisher;
    }

    public Community saveCommunity(Community community) {
        communityRepository.save(community);
        communityCreatedPublisher.publishCommunityCreatedEvent(community);
        return community;
    }
}
