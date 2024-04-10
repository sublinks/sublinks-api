package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.api.lemmy.v3.user.controllers.UserAuthController;
import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.events.PersonCreatedEvent;
import com.sublinks.sublinksapi.person.events.PersonUpdatedEvent;
import com.sublinks.sublinksapi.queue.services.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RabbitTemplate.class)
public class PersonActorCreateListener implements ApplicationListener<PersonCreatedEvent> {
  @Value("${sublinks.federation.exchange}")
  private String federationExchange;

  Producer federationProducer;

  private static final Logger logger = LoggerFactory.getLogger(PersonActorCreateListener.class);

  @Override
  public void onApplicationEvent(PersonCreatedEvent event) {
    Person person = event.getPerson();

    final Actor actorMessage = Actor.builder()
        .actor_id(person.getActorId())
        .actor_type(ActorType.USER.getValue())
        .bio(person.getBiography())
        .display_name(person.getName())
        .matrix_user_id(person.getMatrixUserId())
        .private_key(person.getPrivateKey())
        .public_key(person.getPublicKey())
        .build();

    federationProducer.sendMessage(federationExchange, RoutingKey.ACTOR_CREATE.getValue(), actorMessage);
    logger.info(String.format("person actor created %s", person.getActorId()));
  }
}
