package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.events.CommunityCreatedEvent;
import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.models.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RabbitTemplate.class)
public class CommunityActorCreateListener extends FederationListener implements
    ApplicationListener<CommunityCreatedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(CommunityActorCreateListener.class);

  @Override
  public void onApplicationEvent(CommunityCreatedEvent event) {

    Community community = event.getCommunity();

    final Actor actorMessage = Actor.builder()
        .actor_id(community.getActivityPubId())
        .actor_type(ActorType.COMMUNITY.getValue())
        .bio(community.getDescription())
        .display_name(community.getTitle())
        .private_key(community.getPrivateKey())
        .public_key(community.getPublicKey())
        .build();

    federationProducer.sendMessage(federationExchange, RoutingKey.ACTOR_CREATE.getValue(),
        actorMessage);
    logger.info(String.format("community actor created %s", community.getActivityPubId()));
  }
}
