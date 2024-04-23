package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.events.CommunityCreatedEvent;
import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.queue.services.Producer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class CommunityActorCreateListener implements ApplicationListener<CommunityCreatedEvent> {

  @Value("${sublinks.federation.exchange}")
  private String federationExchange;
  final private Producer federationProducer;

  private static final Logger logger = LoggerFactory.getLogger(CommunityActorCreateListener.class);

  @Override
  public void onApplicationEvent(@NonNull CommunityCreatedEvent event) {

    if (getFederationProducer() == null) {
      logger.error("federation producer is not instantiated properly");
      return;
    }

    Community community = event.getCommunity();

    final Actor actorMessage = Actor.builder()
        .id(community.getActivityPubId())
        .actor_type(ActorType.COMMUNITY.getValue())
        .bio(community.getDescription())
        .username(community.getTitleSlug())
        .name(community.getTitle())
        .private_key(community.getPrivateKey())
        .public_key(community.getPublicKey())
        .build();

    getFederationProducer().sendMessage(getFederationExchange(), RoutingKey.ACTOR_CREATE.getValue(),
        actorMessage);
    logger.info(String.format("community actor created %s", community.getActivityPubId()));
  }
}
