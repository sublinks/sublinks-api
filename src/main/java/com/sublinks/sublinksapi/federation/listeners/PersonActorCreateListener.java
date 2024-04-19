package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.events.PersonCreatedEvent;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PersonActorCreateListener extends FederationListener implements
    ApplicationListener<PersonCreatedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(PersonActorCreateListener.class);

  @Override
  public void onApplicationEvent(@NonNull PersonCreatedEvent event) {

    if (federationProducer == null) {
      logger.error("federation producer is not instantiated properly");
      return;
    }

    Person person = event.getPerson();

    final Actor actorMessage = Actor.builder()
        .actor_id(person.getActorId())
        .actor_type(ActorType.USER.getValue())
        .bio(person.getBiography())
        .display_name(person.getName())
        .username(person.getUsername())
        .matrix_user_id(person.getMatrixUserId())
        .private_key(person.getPrivateKey())
        .public_key(person.getPublicKey())
        .build();

    federationProducer.sendMessage(federationExchange, RoutingKey.ACTOR_CREATE.getValue(),
        actorMessage);
    logger.info(String.format("person actor created %s", person.getActorId()));
  }
}
