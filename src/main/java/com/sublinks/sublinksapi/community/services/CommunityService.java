package com.sublinks.sublinksapi.community.services;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.entities.CommunityAggregate;
import com.sublinks.sublinksapi.community.events.CommunityCreatedPublisher;
import com.sublinks.sublinksapi.community.repositories.CommunityAggregateRepository;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.utils.KeyGeneratorUtil;
import com.sublinks.sublinksapi.utils.KeyStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class provides methods for creating and updating communities.
 */
@Service
@RequiredArgsConstructor
public class CommunityService {

  private final CommunityRepository communityRepository;
  private final CommunityAggregateRepository communityAggregateRepository;
  private final CommunityCreatedPublisher communityCreatedPublisher;
  private final KeyGeneratorUtil keyGeneratorUtil;
  private final LocalInstanceContext localInstanceContext;

  /**
   * Creates a new community by generating keys, setting activityPubId,
   * and saving it to the repository.
   *
   * @param community The {@link Community} object representing the community to create.
   */
  public void createCommunity(Community community) {

    KeyStore keys = keyGeneratorUtil.generate();
    community.setPrivateKey(keys.privateKey());
    community.setPublicKey(keys.publicKey());
    community.setActivityPubId(
        localInstanceContext.instance().getDomain() + "/c/" + community.getTitleSlug());
    community.setLocal(true);
    // @todo set inbox & follower urls
    communityRepository.save(community);
    final CommunityAggregate communityAggregate = CommunityAggregate.builder()
        .community(community)
        .subscriberCount(1)
        .build();
    communityAggregateRepository.save(communityAggregate);
    community.setCommunityAggregate(communityAggregate);
    communityCreatedPublisher.publish(community);
  }

  /**
   * Updates a community by saving the changes to the repository.
   *
   * @param community The {@link Community} object representing the community to update.
   */
  public void updateCommunity(Community community) {

    communityRepository.save(community);
    // @todo publish edit
  }
}
